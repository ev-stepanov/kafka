package ru.company.kafka.bankaccountgenerator.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.company.kafka.model.rest.GeneratedAccount;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Service
public class AccountsGeneratorServiceImpl implements AccountsGeneratorService {
    public static final int MAX_BALANCE = 10_000_000;
    @Value("${default.generate-names.max-length}")
    private int maxNameLength;

    @Value("${default.generate-names.min-length}")
    private int minNameLength;

    public GeneratedAccount generateAccount() {
        Random random = new Random();
        return GeneratedAccount.builder()
                .uuid(UUID.randomUUID())
                .firstName(RandomStringUtils.randomAlphabetic(minNameLength + random.nextInt(maxNameLength)))
                .lastName(RandomStringUtils.randomAlphabetic(minNameLength + random.nextInt(maxNameLength)))
                .balance((long)random.nextInt(MAX_BALANCE))
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
