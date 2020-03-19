package ru.neoflex.kafka.produser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.kafka.produser.dto.Employee;
import ru.neoflex.kafka.produser.service.Sender;


@RestController
@RequestMapping("/api")
public class TestController {
    private Sender sender;

    @Autowired
    public TestController(Sender sender) {
        this.sender = sender;
    }

    @PostMapping("/employee")
    public void save(@RequestBody Employee employee) {
        sender.sendMessage(employee);
    }
}