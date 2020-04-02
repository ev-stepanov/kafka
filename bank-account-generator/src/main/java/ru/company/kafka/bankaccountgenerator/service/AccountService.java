package ru.company.kafka.bankaccountgenerator.service;

import ru.company.kafka.model.rest.GeneratedAccount;

import java.util.List;

public interface AccountService {
    List<GeneratedAccount> getBankAccounts();
}
