package ru.company.kafka.rsocketserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.company.kafka.rsocketserver.model.BankAccountInfo;

@Configuration
public class RedisConfig {
    @Bean
    public ReactiveRedisTemplate<String, BankAccountInfo> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<BankAccountInfo> valueSerializer =
                new Jackson2JsonRedisSerializer<>(BankAccountInfo.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, BankAccountInfo> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, BankAccountInfo> context =
                builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplateString
            (ReactiveRedisConnectionFactory connectionFactory) {
        return new ReactiveRedisTemplate<>(connectionFactory, RedisSerializationContext.string());
    }
}
