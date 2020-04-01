package ru.company.kafka.bankaccountgenerator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.kafka.bankaccountgenerator.service.AccountService;
import ru.company.kafka.model.rest.GeneratedAccount;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account")
    public ResponseEntity<List<GeneratedAccount>> getAccounts() {
        List<GeneratedAccount> bankAccounts = accountService.getBankAccounts();
        log.info("Were generated accounts: " + bankAccounts);
        return ResponseEntity.ok(bankAccounts);
    }
}