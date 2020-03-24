package ru.company.kafka.bankaccountgenerator.service;

import ru.company.kafka.bankaccountgenerator.model.Account;

import java.util.List;

public interface AccountService {
    Boolean generateBankAccounts();
    List<Account> getBankAccounts(Long count);
    List<Account> getBankAccounts();
}
