package ru.company.kafka.consumertoredis.config;

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
import ru.company.kafka.consumertoredis.model.BankAccountInfo;

import java.time.Duration;

@Configuration
public class LettuceConfig {

    @Bean
    @Primary
    public ReactiveRedisConnectionFactory connectionFactory() {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(2))
                .shutdownTimeout(Duration.ZERO)
                .build();

        return new LettuceConnectionFactory(
                new RedisStandaloneConfiguration("localhost", 6379), clientConfig);
    }

    @Bean
    public ReactiveRedisTemplate<String, BankAccountInfo> reactiveRedisTemplate(ReactiveRedisConnectionFactory redisConnectionFactory) {
        return new ReactiveRedisTemplate<>(redisConnectionFactory, RedisSerializationContext.<String, BankAccountInfo>newSerializationContext()
                .key(new StringRedisSerializer())
                .value(new Jackson2JsonRedisSerializer<>(BankAccountInfo.class))
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(BankAccountInfo.class))
                .string(new StringRedisSerializer())
                .build());
    }
}
