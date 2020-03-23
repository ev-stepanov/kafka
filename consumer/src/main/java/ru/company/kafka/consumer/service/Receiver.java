package ru.company.kafka.consumer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.company.kafka.consumer.dto.AccountDto;

@Service
@Log4j2
public class Receiver {
    private AccountService accountService;

    @Autowired
    public Receiver(AccountService accountService) {
        this.accountService = accountService;
    }

    @KafkaListener(topics = "topic")
    public void listen(AccountDto accountDto) {
        log.info("Received message: " + accountDto);
        accountService.save(accountDto);
    }
}
