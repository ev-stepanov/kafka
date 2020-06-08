package ru.company.kafka.service;

import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;
import ru.company.kafka.dto.FilterByTypeAccountDto;
import ru.company.kafka.model.BankAccountInfo;
import ru.company.kafka.model.enums.TypeAccount;
import ru.company.kafka.repository.BankAccountRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
@GRpcService
@RequiredArgsConstructor
public class BankAccountService extends BankAccountInfoServiceGrpc.BankAccountInfoServiceImplBase {
    private final BankAccountRepository bankAccountRepository;
    private final CassandraTemplate cassandraTemplate;

    @Override
    public void getAllBankAccounts(Empty request, StreamObserver<BankAccountsProto> responseObserver) {
        log.info("Server received {}", request);

        List<BankAccountInfo> bankAccounts = bankAccountRepository.findAll();
        BankAccountsProto bankAccountsProto = mapBankAccountsToProto(bankAccounts);

        responseObserver.onNext(bankAccountsProto);
        responseObserver.onCompleted();
        log.info("Server responsed {}", bankAccountsProto);
    }

    @Override
    public void getBankAccountsByAccountType(AccountTypeRequestProto request, StreamObserver<BankAccountsProto> responseObserver) {
        log.info("Server received {}", request);
        TypeAccount typeAccount = TypeAccount.valueOf(request.getAccountType().name());

        List<FilterByTypeAccountDto> listUuidByTypeAccounts = cassandraTemplate.getCqlOperations().query(
                "SELECT uuid, bank_account.typeAccount FROM bank_account_info",
                (row, rowNum) -> FilterByTypeAccountDto.builder()
                        .uuid(row.getUUID("uuid"))
                        .typeAccount(TypeAccount.valueOf(row.getString("bank_account.typeAccount")))
                        .build());

        List<UUID> uuids = listUuidByTypeAccounts
                .parallelStream()
                .filter(account -> account.getTypeAccount() == typeAccount)
                .map(FilterByTypeAccountDto::getUuid)
                .collect(Collectors.toList());

        List<BankAccountInfo> bankAccountsByType = bankAccountRepository.findAllById(uuids);
        BankAccountsProto bankAccountsProto = mapBankAccountsToProto(bankAccountsByType);

        responseObserver.onNext(bankAccountsProto);
        responseObserver.onCompleted();
        log.info("Server responded {}", bankAccountsProto);
    }

    private BankAccountsProto mapBankAccountsToProto(List<BankAccountInfo> bankAccountsByType) {
        BankAccountsProto.Builder builder = BankAccountsProto.newBuilder();
        for (BankAccountInfo account : bankAccountsByType) {
            AddressProto address = AddressProto.newBuilder()
                    .setState(account.getAddress().getState())
                    .setCity(account.getAddress().getCity())
                    .setStreet(account.getAddress().getStreet())
                    .build();
            BankAccountProto bankAccount = BankAccountProto.newBuilder()
                    .setFirstName(account.getBankAccount().getFirstName())
                    .setLastName(account.getBankAccount().getLastName())
                    .setBirthday(fromLocalDateToTimestamp(account.getBankAccount().getBirthday()))
                    .setBalance(account.getBankAccount().getBalance())
                    .setType(TypeAccountProto.valueOf(account.getBankAccount().getTypeAccount().name()))
                    .build();
            BankAccountInfoProto bankAccountInfo = BankAccountInfoProto.newBuilder()
                    .setUuid(account.getUuid().toString())
                    .setAddress(address)
                    .setBankAccount(bankAccount)
                    .build();
            builder.addBankAccountInfo(bankAccountInfo);
        }
        return builder.build();
    }

    private static Timestamp fromLocalDateToTimestamp(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
