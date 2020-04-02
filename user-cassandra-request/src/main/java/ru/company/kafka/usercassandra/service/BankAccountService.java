package ru.company.kafka.usercassandra.service;

import ru.company.kafka.usercassandra.model.BankAccountInfo;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    Optional<BankAccountInfo> findById(String uuid);
    List<BankAccountInfo> findAll();
}
