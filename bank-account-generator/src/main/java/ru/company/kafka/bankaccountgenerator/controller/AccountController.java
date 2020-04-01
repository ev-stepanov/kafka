package ru.company.kafka.bankaccountgenerator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.kafka.bankaccountgenerator.service.AccountService;
import ru.company.kafka.model.rest.GeneratedAccount;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account")
    public ResponseEntity<List<GeneratedAccount>> getAccounts() {
        return ResponseEntity.ok(accountService.getBankAccounts());
    }
}