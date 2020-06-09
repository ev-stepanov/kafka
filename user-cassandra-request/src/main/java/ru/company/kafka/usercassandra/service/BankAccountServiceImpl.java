package ru.company.kafka.usercassandra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.kafka.usercassandra.model.BankAccountInfo;
import ru.company.kafka.usercassandra.repository.BankAccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    @Override
    public Optional<BankAccountInfo> findById(String uuid) {
        return bankAccountRepository.findById(UUID.fromString(uuid));
    }

    @Override
    public List<BankAccountInfo> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public long count() {
        return bankAccountRepository.count();
    }
}