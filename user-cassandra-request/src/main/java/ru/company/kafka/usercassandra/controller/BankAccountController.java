package ru.company.kafka.usercassandra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.kafka.bankaccountredismodel.BankAccountInfo;
import ru.company.kafka.usercassandra.service.BankAccountService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @GetMapping("/accounts/{uuid}")
    public ResponseEntity<BankAccountInfo> getBankAccount(@PathVariable String uuid) {
        return bankAccountService.findById(uuid)
                .map(bankAccountInfo -> new ResponseEntity<>(bankAccountInfo, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/accounts")
    public List<BankAccountInfo> getBankAccounts() {
        return bankAccountService.findAll();
    }

    @GetMapping ("/accounts/size")
    public Long getCountBankAccounts() {
        return bankAccountService.count();
    }
}
