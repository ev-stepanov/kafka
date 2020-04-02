package ru.company.kafka.bankaccountgenerator.service;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import ru.company.kafka.model.rest.GeneratedAccount;

import java.time.ZoneId;
import java.util.UUID;

@Service
public class AccountsGeneratorServiceImpl implements AccountsGeneratorService {
    private final Faker faker;

    public AccountsGeneratorServiceImpl(Faker faker) {
        this.faker = faker;
    }

    public GeneratedAccount generateAccount() {
        return GeneratedAccount.builder()
                .uuid(UUID.randomUUID())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .balance(Double.parseDouble(faker.commerce().price().replace(",",".")))
                .birthday(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .build();
    }
}
