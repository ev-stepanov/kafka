package ru.company.kafka.usercassandra.service;

import ru.company.kafka.usercassandra.model.BankAccount;

import java.util.List;

public interface BankAccountService {
    List<BankAccount> findByLastName(String lastName);
    List<BankAccount> findAll();
}
