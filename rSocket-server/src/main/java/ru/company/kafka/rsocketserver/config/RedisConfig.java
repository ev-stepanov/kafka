package ru.company.kafka.rsocketserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.company.kafka.rsocketserver.model.BankAccountInfo;

@Configuration
public class RedisConfig {
    @Bean
    ReactiveRedisOperations<String, BankAccountInfo> redisOperations(ReactiveRedisConnectionFactory factory) {
        RedisSerializationContext<String, BankAccountInfo> serializationContext = RedisSerializationContext
                .<String, BankAccountInfo>newSerializationContext(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(BankAccountInfo.class))
                .build();

        return new ReactiveRedisTemplate<>(factory, serializationContext);
    }
}