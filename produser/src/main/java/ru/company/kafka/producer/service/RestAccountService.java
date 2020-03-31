package ru.company.kafka.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.company.kafka.model.Account;
import ru.company.kafka.model.TypeBankAccount;
import ru.company.kafka.producer.dto.RestAccount;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class RestAccountService {
    private RestTemplate restTemplate;
    private Sender sender;

    @Autowired
    public RestAccountService(RestTemplate restTemplate, Sender sender) {
        this.restTemplate = restTemplate;
        this.sender = sender;
    }

    @Scheduled(cron = "0/5 * * * * *")
    public void fetchAccounts() {
        ResponseEntity<RestAccount[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8085/api/generate/accounts",
                        RestAccount[].class);

        List<RestAccount> accounts = Arrays.asList(Objects.requireNonNull(response.getBody()));
        accounts.stream().map(this::mapRestAccountToAccount).parallel().forEach(a -> sender.sendMessage(a));
    }

    private Account mapRestAccountToAccount(RestAccount restAccount) {

        return Account.builder()
                .uuid(restAccount.getUuid())
                .firstName(restAccount.getFirstName())
                .lastName(restAccount.getLastName())
                .birthday(restAccount.getBirthday())
                .balance(restAccount.getBalance())
                .typeBankAccount(new Random().nextInt(2) == 1 ? TypeBankAccount.CREDIT : TypeBankAccount.DEBIT)
                .build();
    }
}
