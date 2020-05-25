package ru.company.kafka.rsocketserver.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.company.kafka.rsocketserver.model.BankAccountInfo;

import javax.annotation.PostConstruct;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisBankAccountRepository {
    private final ReactiveRedisOperations<String, BankAccountInfo> operations;
    private ReactiveValueOperations<String, BankAccountInfo> reactiveValueOperations;
    private ReactiveHashOperations<String, Object, Object> hash;

    @PostConstruct
    public void setup() {
        reactiveValueOperations = operations.opsForValue();
        hash = operations.opsForHash();
    }


    public Mono<BankAccountInfo> findById(String uuid) {
        return Mono.empty();
    }
}
