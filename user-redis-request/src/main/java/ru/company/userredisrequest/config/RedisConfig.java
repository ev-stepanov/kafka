package ru.company.userredisrequest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.company.userredisrequest.model.BankAccountInfo;

import java.time.Duration;

@Configuration
public class RedisConfig {
    @Bean
    @Primary
    public ReactiveRedisConnectionFactory connectionFactory() {
        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                .shutdownTimeout(Duration.ZERO)
                .commandTimeout(Duration.ofSeconds(1))
                .build();
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379), lettuceClientConfiguration);
    }

    @Bean
    public ReactiveRedisTemplate<String, BankAccountInfo> reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, RedisSerializationContext.<String, BankAccountInfo>newSerializationContext()
                .key(new StringRedisSerializer())
                .value(new Jackson2JsonRedisSerializer<>(BankAccountInfo.class))
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(BankAccountInfo.class))
                .build());
    }
}
