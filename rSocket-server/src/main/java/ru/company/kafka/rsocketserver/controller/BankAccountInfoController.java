package ru.company.kafka.rsocketserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.company.kafka.rsocketserver.model.BankAccountInfo;
import ru.company.kafka.rsocketserver.service.BankAccountService;

@RestController
@RequiredArgsConstructor
public class BankAccountInfoController {
    private final BankAccountService bankAccountService;

    @MessageMapping("accounts")
    public Flux<BankAccountInfo> getAllBankAccounts() {
        return bankAccountService.findAll();
    }

    @MessageMapping("accounts-count")
    public Mono<Long> getCountBankAccounts(){
        return bankAccountService.count();
    }
}
