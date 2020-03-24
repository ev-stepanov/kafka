package ru.company.kafka.bankaccountgenerator.service;

import ru.company.kafka.bankaccountgenerator.model.Account;

public interface AccountsGeneratorService {
    Account generateAccount();
}
