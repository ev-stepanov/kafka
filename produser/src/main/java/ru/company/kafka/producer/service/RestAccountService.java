package ru.company.kafka.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.company.kafka.producer.dto.Account;

import java.util.Arrays;
import java.util.Objects;

@Service
public class RestAccountService {
    private RestTemplate restTemplate;
    private Sender sender;

    @Autowired
    public RestAccountService(RestTemplate restTemplate, Sender sender) {
        this.restTemplate = restTemplate;
        this.sender = sender;
    }

    @Scheduled(cron = "* * * * * *")
    public void fetchAccounts() {
        ResponseEntity<Account[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8080/api/generate/accounts",
                        Account[].class);

        Arrays.asList(Objects.requireNonNull(response.getBody())).parallelStream().forEach(a -> sender.sendMessage(a));
    }
}
