package ru.company.kafka.usercassandra.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.company.kafka.usercassandra.model.BankAccount;
import ru.company.kafka.usercassandra.repository.BankAccountRepository;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public List<BankAccount> findByLastName(String lastName) {
        return bankAccountRepository.findByLastName(lastName);
    }

    @Override
    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }
}