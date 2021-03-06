package ru.company.kafka.bankaccountgenerator.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public Faker faker() {
        return new Faker();
    }
}
