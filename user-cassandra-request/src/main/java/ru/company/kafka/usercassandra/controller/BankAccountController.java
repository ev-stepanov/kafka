package ru.company.kafka.usercassandra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.kafka.usercassandra.model.BankAccountInfo;
import ru.company.kafka.usercassandra.service.BankAccountService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @GetMapping("/account/{uuid}")
    public Optional<BankAccountInfo> getBankAccount(@PathVariable String uuid) {
        return bankAccountService.findById(uuid);
    }

    @GetMapping("/account")
    public List<BankAccountInfo> getBankAccounts() {
        return bankAccountService.findAll();
    }

    @GetMapping ("/account/size")
    public Long getCountBankAccounts() {
        return bankAccountService.count();
    }
}
