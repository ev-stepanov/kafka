package ru.company.userredisrequest.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.company.userredisrequest.dto.BankAccountInfoSaveDto;
import ru.company.userredisrequest.model.BankAccountInfo;


public interface BankAccountService {
    Mono<BankAccountInfo> findById(String uuid);
    Mono<BankAccountInfo> save(BankAccountInfoSaveDto bankAccountInfoSaveDto);
    Mono<Void> deleteById(String uuid);
    Flux<String> getAllKeys();
}
