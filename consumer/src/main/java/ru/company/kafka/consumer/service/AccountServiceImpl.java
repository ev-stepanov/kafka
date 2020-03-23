package ru.company.kafka.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.company.kafka.consumer.dto.AccountDto;
import ru.company.kafka.consumer.model.Account;
import ru.company.kafka.consumer.repository.AccountRepository;

import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(AccountDto accountDto) {
        Account account = Account.builder()
                .uuid(UUID.randomUUID())
                .balance(accountDto.getBalance())
                .birthday(accountDto.getBirthday())
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .build();
        return accountRepository.save(account);
    }
}
