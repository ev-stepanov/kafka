package ru.company.kafka.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import ru.company.kafka.model.producer.BankAccount;
import ru.company.kafka.model.producer.TypeAccount;
import ru.company.kafka.model.rest.GeneratedAccount;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
public class RestAccountService {
    public static final String API_ACCOUNTS = "http://localhost:8085/api/account";

    private RestTemplate restTemplate;
    private Producer producer;

    @Autowired
    public RestAccountService(RestTemplate restTemplate, Producer producer) {
        this.restTemplate = restTemplate;
        this.producer = producer;
    }

    @Scheduled(cron = "0/5 * * * * *")
    public void fetchAccounts() {
        ResponseEntity<GeneratedAccount[]> response;
        try {
            response = restTemplate.getForEntity(API_ACCOUNTS, GeneratedAccount[].class);
        } catch (ResourceAccessException | HttpClientErrorException e) {
            log.error(e.getMessage());
            return;
        }
        List<GeneratedAccount> accounts = Arrays.asList(Objects.requireNonNull(response.getBody()));
        log.info("The data has been downloaded from the source: " + API_ACCOUNTS);

        accounts.stream()
                .parallel()
                .filter(account -> account.getBirthday().isBefore(LocalDate.now().minusYears(18))) // Client have to be 18+ old
                .map(this::mapGeneratedAccountToAccount)
                .forEach(a -> producer.sendMessage(a));
    }

    private BankAccount mapGeneratedAccountToAccount(GeneratedAccount account) {
        return BankAccount.builder()
                .uuid(account.getUuid())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .birthday(account.getBirthday())
                .balance(account.getBalance())
                .typeAccount(new Random().nextInt(2) == 1 ? TypeAccount.CREDIT : TypeAccount.DEBIT)
                .build();
    }
}
