package ru.company.kafka.usercassandra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.kafka.usercassandra.model.BankAccount;
import ru.company.kafka.usercassandra.service.BankAccountService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankAccountController {

    private BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/account/{lastName}")
    public ResponseEntity<List<BankAccount>> getBankAccount(@PathVariable String lastName) {
        return ResponseEntity.ok(bankAccountService.findByLastName(lastName));
    }

    @GetMapping("/accounts")
    public List<BankAccount> getBankAccounts() {
        return bankAccountService.findAll();
    }
}
