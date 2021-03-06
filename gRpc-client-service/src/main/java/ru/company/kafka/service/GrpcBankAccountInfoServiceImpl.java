package ru.company.kafka.service;

import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.company.kafka.model.Address;
import ru.company.kafka.model.BankAccount;
import ru.company.kafka.model.BankAccountInfo;
import ru.company.kafka.model.enums.TypeAccount;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GrpcBankAccountInfoServiceImpl implements GrpcBankAccountInfoService {
    private BankAccountInfoServiceGrpc.BankAccountInfoServiceBlockingStub bankAccountInfoServiceBlockingStub;

    @Value("${grpc.host}")
    private String host;

    @Value("${grpc.port}")
    private Integer port;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        bankAccountInfoServiceBlockingStub =
                BankAccountInfoServiceGrpc.newBlockingStub(managedChannel);
    }

    @Override
    public List<BankAccountInfo> getBankAccountsByAccountType(TypeAccount typeAccount) {
        log.info("client sending {}", typeAccount);

        AccountTypeRequestProto accountType = AccountTypeRequestProto.newBuilder()
                .setAccountType(TypeAccountProto.valueOf(typeAccount.name()))
                .build();

        BankAccountsProto bankAccountsByAccountType = bankAccountInfoServiceBlockingStub.getBankAccountsByAccountType(accountType);
        log.info("client received {}", bankAccountsByAccountType);

        return mapBankAccountProtoToBankAccount(bankAccountsByAccountType);
    }

    @Override
    public List<BankAccountInfo> getAllBankAccounts() {
        log.info("'getAllBankAccounts' method  was started without parameters");

        Empty empty = Empty.newBuilder().build();
        BankAccountsProto allBankAccounts = bankAccountInfoServiceBlockingStub.getAllBankAccounts(empty);

        log.info("client received data {} from 'getAllBankAccounts' method", allBankAccounts);

        return mapBankAccountProtoToBankAccount(allBankAccounts);
    }

    private List<BankAccountInfo> mapBankAccountProtoToBankAccount(BankAccountsProto bankAccountsProto) {
        return bankAccountsProto.getBankAccountInfoList()
                .parallelStream()
                .map(bankAccountInfoProto -> BankAccountInfo.builder()
                        .address(Address.builder()
                                .street(bankAccountInfoProto.getAddress().getStreet())
                                .state(bankAccountInfoProto.getAddress().getState())
                                .city(bankAccountInfoProto.getAddress().getCity())
                                .build())
                        .bankAccount(BankAccount.builder()
                                .firstName(bankAccountInfoProto.getBankAccount().getFirstName())
                                .lastName(bankAccountInfoProto.getBankAccount().getLastName())
                                .balance(bankAccountInfoProto.getBankAccount().getBalance())
                                .typeAccount(TypeAccount.valueOf(bankAccountInfoProto.getBankAccount().getType().name()))
                                .birthday(fromTimestampToLocalDate(bankAccountInfoProto.getBankAccount().getBirthday()))
                                .build())
                        .uuid(UUID.fromString(bankAccountInfoProto.getUuid()))
                        .build())
                .collect(Collectors.toList());
    }

    private static LocalDate fromTimestampToLocalDate(Timestamp timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()), ZoneId.of("UTC"))
                .toLocalDate();
    }
}
