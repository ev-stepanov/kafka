package ru.company.kafka.rsocketserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.company.kafka.rsocketserver.model.BankAccountInfo;
import ru.company.kafka.rsocketserver.repository.RedisBankAccountRepository;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final RedisBankAccountRepository repository;

    @Override
    public Flux<BankAccountInfo> findAll() {                
        return null;
    }

    @Override
    public Mono<BankAccountInfo> findByUuid(String uuid) {
        return repository.findById(uuid);
    }

    @Override
    public Mono<Long> count() {
        return null;
    }
}
