package ru.company.kafka.rsocketserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.company.kafka.rsocketserver.model.BankAccountInfo;
import ru.company.kafka.rsocketserver.repository.CassandraRepository;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final CassandraRepository cassandraRepository;

    @Override
    public Flux<BankAccountInfo> findAll() {
        return cassandraRepository.findAll();
    }

    @Override
    public Mono<Long> count() {
        return cassandraRepository.count();
    }
}
