package ru.company.kafka.service;

import ru.company.kafka.model.BankAccountInfo;
import ru.company.kafka.model.enums.TypeAccount;

import java.util.List;

public interface GrpcBankAccountInfoService {
    List<BankAccountInfo> getBankAccountsByAccountType(TypeAccount typeAccount);
}
