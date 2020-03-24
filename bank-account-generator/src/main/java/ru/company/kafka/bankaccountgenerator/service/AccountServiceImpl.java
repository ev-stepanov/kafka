package ru.company.kafka.bankaccountgenerator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.company.kafka.bankaccountgenerator.model.Account;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class AccountServiceImpl implements AccountService {
    private AccountsGeneratorService accountsGeneratorService;

    @Value("${account.count.default}")
    private Long countGenerateAccounts;

    @Value(("${path.generated.accounts}"))
    private String path;

    @Autowired
    public AccountServiceImpl(AccountsGeneratorService accountsGeneratorService) {
        this.accountsGeneratorService = accountsGeneratorService;
    }

    @Override
    public Boolean generateBankAccounts() {
        try(FileOutputStream f = new FileOutputStream(new File(path + "accounts.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f)) {
            for (int i = 0; i < countGenerateAccounts; i++) {
                o.writeObject(accountsGeneratorService.generateAccount());
            }
        } catch (IOException e) {
            log.error("Error writing to file", e);
        }

        return true;
    }

    @Override
    public List<Account> getBankAccounts(Long count) {
        File accountsFile = new File(path + "accounts.txt");
        if(!accountsFile.exists()) {
            generateBankAccounts();
        }

        List<Account> accounts = new ArrayList<>();

        try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream(accountsFile))) {
            for (int i = 0; i < count; i++) {
                accounts.add((Account) oi.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error writing to accountsFile", e);
        }

        return accounts;
    }

    @Override
    public List<Account> getBankAccounts() {
        return getBankAccounts(countGenerateAccounts);
    }
}
