package ru.company.kafka.consumer.service;


import ru.company.kafka.consumer.dto.AccountDto;
import ru.company.kafka.consumer.model.Account;

public interface AccountService {
    Account save(AccountDto account);
}
