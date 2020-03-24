package ru.company.kafka.bankaccountgenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.kafka.bankaccountgenerator.model.Account;
import ru.company.kafka.bankaccountgenerator.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/generation")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts/{count}")
    public ResponseEntity<List<Account>> getAccounts(@PathVariable Long count) {
        return ResponseEntity.ok(accountService.getBankAccounts(count));
    }

    @PostMapping("/account")
    public ResponseEntity<Boolean> generateAccounts() {
        return ResponseEntity.ok(accountService.generateBankAccounts());
    }
}
