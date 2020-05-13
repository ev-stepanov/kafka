package ru.company.kafka.rsocketserver.service;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStreamOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.company.kafka.rsocketserver.model.BankAccountInfo;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final ReactiveRedisTemplate<String, BankAccountInfo> redisTemplate;

    private ReactiveStreamOperations<String, Object, Object> reactiveValueOps;

    @PostConstruct
    public void setup() {
        reactiveValueOps = redisTemplate.opsForStream();
    }

    @Override
    public Flux<BankAccountInfo> findAll() {
        return redisTemplate
                .keys("*")
                .flatMap(s -> Subscriber::onComplete);
    }

    @Override
    public Mono<Long> count() {
        return redisTemplate
                .keys("*")
                .count();
    }
}
