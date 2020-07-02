package ru.company.userredisrequest.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.company.userredisrequest.exception.BankAccountInfoNotFound;
import ru.company.userredisrequest.model.BankAccountInfo;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SimpleReactiveBankAccountInfoRepository implements ReactiveCrudRepository<BankAccountInfo, String> {
    private final ReactiveRedisTemplate<String, BankAccountInfo> redisTemplate;

    @Override
    public <S extends BankAccountInfo> Mono<S> save(S entity) {
        return redisTemplate.opsForValue()
                .set(keyWithPrefix(entity.getUuid()), entity)
                .flatMap(result -> result
                        ? Mono.just(entity)
                        : Mono.error(Exception::new));
    }

    @Override
    public <S extends BankAccountInfo> Flux<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends BankAccountInfo> Flux<S> saveAll(Publisher<S> entityStream) {
        return null;
    }

    @Override
    public Mono<BankAccountInfo> findById(String uuid) {
        return redisTemplate.opsForValue()
                .get(keyWithPrefix(uuid))
                .flatMap(result -> result != null
                        ? Mono.just(result)
                        : Mono.error(new BankAccountInfoNotFound("not found")));
    }

    @Override
    public Mono<BankAccountInfo> findById(Publisher<String> id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(String s) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher<String> id) {
        return null;
    }

    @Override
    public Flux<BankAccountInfo> findAll() {
        return null;
    }

    @Override
    public Flux<BankAccountInfo> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public Flux<BankAccountInfo> findAllById(Publisher<String> idStream) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(String uuid) {
        return redisTemplate.opsForValue()
                .delete(keyWithPrefix(uuid))
                .then();
    }

    @Override
    public Mono<Void> deleteById(Publisher<String> id) {
        return null;
    }

    @Override
    public Mono<Void> delete(BankAccountInfo entity) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends BankAccountInfo> entities) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends BankAccountInfo> entityStream) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }

    private String keyWithPrefix(String uuid) {
        return BankAccountInfo.BANK_ACCOUNT_NAME_KEY + uuid;
    }
}
