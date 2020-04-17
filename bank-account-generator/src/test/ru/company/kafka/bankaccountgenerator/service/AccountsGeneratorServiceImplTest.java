package ru.company.kafka.bankaccountgenerator.service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.company.kafka.model.rest.GeneratedAccount;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class AccountsGeneratorServiceImplTest {
    @InjectMocks
    private AccountsGeneratorServiceImpl accountsGeneratorService;

    @Spy
    public Faker faker;

    @Test
    void testGenerateAccount() {
        GeneratedAccount result = accountsGeneratorService.generateAccount();

        assertNotNull(result.getBalance());
        assertNotNull(result.getFirstName());
        assertNotNull(result.getLastName());
        assertNotNull(result.getBirthday());
        verify(faker, times(2)).name();
        verify(faker, times(1)).commerce();
        verify(faker, times(1)).date();
    }
}