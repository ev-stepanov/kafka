package ru.company.kafka.rsocketserver.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.company.kafka.rsocketserver.model.BankAccountInfo;

public interface BankAccountService {
    Flux<BankAccountInfo> findAll();
    Mono<BankAccountInfo> findByUuid(String uuid);
    Mono<Long> count();
}
