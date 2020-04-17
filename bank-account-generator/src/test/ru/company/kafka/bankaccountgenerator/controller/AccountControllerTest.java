package ru.company.kafka.bankaccountgenerator.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.company.kafka.bankaccountgenerator.service.AccountServiceImpl;
import ru.company.kafka.model.rest.GeneratedAccount;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class AccountControllerTest {
    private static final String FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Sidorov";
    private static final double BALANCE = 125.48;
    private static final int YEAR = 1987;
    private static final int MONTH = 10;
    private static final int DAY = 15;
    private static final int EXPECTED_COLLECTION_SIZE = 1;

    private MockMvc mockMvc;

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountServiceImpl accountService;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc =  MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void testGetOneAccount() throws Exception {
        UUID uuid = UUID.randomUUID();
        when(accountService.getBankAccounts())
                .thenReturn(Collections.singletonList(GeneratedAccount.builder()
                        .uuid(uuid)
                        .firstName(FIRST_NAME)
                        .lastName(LAST_NAME)
                        .birthday(LocalDate.of(YEAR, MONTH, DAY))
                        .balance(BALANCE)
                        .build()));


        mockMvc.perform(get("/api/accounts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(EXPECTED_COLLECTION_SIZE)))
                .andExpect(jsonPath("$[0].firstName").value(FIRST_NAME))
                .andExpect(jsonPath("$[0].lastName").value(LAST_NAME))
                .andExpect(jsonPath("$[0].balance").value(BALANCE))
                .andExpect(jsonPath("$[0].uuid").value(uuid.toString()))
                .andExpect(jsonPath("$[0].birthday[0]").value(YEAR))
                .andExpect(jsonPath("$[0].birthday[1]").value(MONTH))
                .andExpect(jsonPath("$[0].birthday[2]").value(DAY));
    }
}