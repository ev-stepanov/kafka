package ru.company.kafka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.kafka.model.BankAccountInfo;
import ru.company.kafka.model.enums.TypeAccount;
import ru.company.kafka.service.GrpcBankAccountInfoService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BankAccountInfoController {
    private final GrpcBankAccountInfoService grpcBankAccountInfoService;

    @GetMapping("/account/{type}")
    public ResponseEntity<List<BankAccountInfo>> getBankAccountsByTypeAccount(@PathVariable TypeAccount type) {
        return ResponseEntity.ok(grpcBankAccountInfoService.getBankAccountsByAccountType(type));
    }

    @GetMapping("/account")
    public ResponseEntity<List<BankAccountInfo>> getAllBankAccounts() {
        return ResponseEntity.ok(grpcBankAccountInfoService.getAllBankAccounts());
    }
}
