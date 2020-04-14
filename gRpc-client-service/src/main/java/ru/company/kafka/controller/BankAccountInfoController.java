package ru.company.kafka.controller;

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
public class BankAccountInfoController {
    private final GrpcBankAccountInfoService grpcBankAccountInfoService;

    public BankAccountInfoController(GrpcBankAccountInfoService grpcBankAccountInfoService) {
        this.grpcBankAccountInfoService = grpcBankAccountInfoService;
    }

    @GetMapping("/accounts/{type}")
    public ResponseEntity<List<BankAccountInfo>> getBankAccountsByTypeAccount(@PathVariable TypeAccount type) {
        return ResponseEntity.ok().body(grpcBankAccountInfoService.getBankAccountsByAccountType(type));
    }
}
