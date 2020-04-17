package ru.company.kafka.bankaccountgenerator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import ru.company.kafka.model.rest.GeneratedAccount;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class AccountServiceTest {
    private static final String FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Sidorov";
    private static final double BALANCE = 125.48;
    private static final LocalDate BIRTHDAY = LocalDate.of(1987, 10, 15);
    private static final int EXPECTED_BANK_ACCOUNTS_SIZE = 1;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountsGeneratorServiceImpl accountsGeneratorService;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(accountService, "countGenerateAccounts", 1L, Long.class);
    }

    @Test
    void testGetBankAccounts() {
        UUID uuid = UUID.randomUUID();
        when(accountsGeneratorService.generateAccount())
                .thenReturn(GeneratedAccount.builder()
                        .uuid(uuid)
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .birthday(BIRTHDAY)
                        .balance(BALANCE)
                        .build());

        List<GeneratedAccount> bankAccounts = accountService.getBankAccounts();

        verify(accountsGeneratorService, times(1)).generateAccount();

        assertEquals(EXPECTED_BANK_ACCOUNTS_SIZE, bankAccounts.size());
        GeneratedAccount account = bankAccounts.get(0);
        assertEquals(FIRST_NAME, account.getFirstName());
        assertEquals(LAST_NAME, account.getLastName());
        assertEquals(BALANCE, account.getBalance(), 0.0001);
        assertEquals(BIRTHDAY, account.getBirthday());
        assertEquals(uuid, account.getUuid());
    }
}