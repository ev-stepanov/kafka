package ru.company.kafka.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.kafka.producer.dto.Account;
import ru.company.kafka.producer.service.Sender;

@RestController
@RequestMapping("/api")
public class AccountController {
    private Sender sender;

    @Autowired
    public AccountController(Sender sender) {
        this.sender = sender;
    }

    @PostMapping("/account")
    public void save(@RequestBody Account account) {
        sender.sendMessage(account);
    }
}