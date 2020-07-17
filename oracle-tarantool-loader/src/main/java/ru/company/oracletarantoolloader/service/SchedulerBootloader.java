package ru.company.oracletarantoolloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.company.oracletarantoolloader.model.oracle.BankAccountInfoOracle;
import ru.company.oracletarantoolloader.model.tarantool.BankAccountInfoTarantool;
import ru.company.oracletarantoolloader.repository.OracleBankAccountInfoRepository;
import ru.company.oracletarantoolloader.repository.TarantoolBankAccountRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerBootloader {
    private final OracleBankAccountInfoRepository oracleBankAccountInfoRepository;
    private final TarantoolBankAccountRepository tarantoolService;

    @Scheduled(cron = "0/30 * * * * *")
    private void loadBankAccountInfoFromOracle() {
        final LocalDateTime now = LocalDateTime.now();
        final List<BankAccountInfoOracle> allNewerAccounts = oracleBankAccountInfoRepository.findAllByCreatedDateBetween(convertToDateViaInstant(now.minusSeconds(30)), convertToDateViaInstant(now));
        allNewerAccounts.parallelStream()
                .map(covertBankAccountOracleToTarantool())
                .peek(bankAccountInfoTarantool -> log.info("Saving bank account with uuid{} to tarantool", bankAccountInfoTarantool.getUuid()))
                .forEach(tarantoolService::save);
    }

    private Function<BankAccountInfoOracle, BankAccountInfoTarantool> covertBankAccountOracleToTarantool() {
        return bankAccountInfoOracle -> BankAccountInfoTarantool.builder()
                .uuid(bankAccountInfoOracle.getUuid())
                .firstName(bankAccountInfoOracle.getFirstName())
                .lastName(bankAccountInfoOracle.getLastName())
                .city(bankAccountInfoOracle.getCity())
                .blackListed(bankAccountInfoOracle.isBlackListed())
                .build();
    }

    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}