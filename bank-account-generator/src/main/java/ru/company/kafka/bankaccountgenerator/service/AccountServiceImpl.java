package ru.company.kafka.bankaccountgenerator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.company.kafka.model.rest.GeneratedAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountsGeneratorService accountsGeneratorService;

    @Value("${default.generate-accounts.count}")
    private Long countGenerateAccounts;

    public AccountServiceImpl(AccountsGeneratorService accountsGeneratorService) {
        this.accountsGeneratorService = accountsGeneratorService;
    }

    @Override
    public List<GeneratedAccount> getBankAccounts() {
        return Stream.generate(accountsGeneratorService::generateAccount)
                .limit(countGenerateAccounts)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
