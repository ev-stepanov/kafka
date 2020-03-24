package ru.company.kafka.bankaccountgenerator.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.company.kafka.bankaccountgenerator.model.Account;

import java.time.LocalDate;
import java.util.*;

@Service
public class AccountsGeneratorServiceImpl implements AccountsGeneratorService {
    private static final int RANDOM_NAME_LENGTH = 10;
    private static final int MIN_NAME_LENGTH = 3;

    public Account generateAccount() {
        Random random = new Random();
        return Account.builder()
                .uuid(UUID.randomUUID())
                .firstName(RandomStringUtils.randomAlphabetic(MIN_NAME_LENGTH + random.nextInt(RANDOM_NAME_LENGTH)))
                .lastName(RandomStringUtils.randomAlphabetic(MIN_NAME_LENGTH + random.nextInt(RANDOM_NAME_LENGTH)))
                .balance((long)random.nextInt(10_000_000))
                .birthday(generateRandomDate())
                .build();
    }

    private LocalDate generateRandomDate() {
        return LocalDate.now().minusDays(randBetween());
    }

    private long randBetween() {
        return (long) 7000 + Math.round(Math.random() * ((long) 30000 - (long) 7000));
    }
}
