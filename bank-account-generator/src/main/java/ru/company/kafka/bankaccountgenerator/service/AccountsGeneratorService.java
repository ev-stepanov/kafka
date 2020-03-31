package ru.company.kafka.bankaccountgenerator.service;

import ru.company.kafka.model.rest.GeneratedAccount;

public interface AccountsGeneratorService {
    GeneratedAccount generateAccount();
}
