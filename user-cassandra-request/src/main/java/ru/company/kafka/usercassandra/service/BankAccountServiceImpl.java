package ru.company.kafka.usercassandra.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.kafka.bankaccountredismodel.BankAccountInfo;
import ru.company.kafka.usercassandra.repository.BankAccountRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    @Override
    public Optional<BankAccountInfo> findById(String uuid) {
        return bankAccountRepository.findById(uuid);
    }

    @Override
    public List<BankAccountInfo> findAll() {
        return Lists.newArrayList(bankAccountRepository.findAll());
    }

    @Override
    public long count() {
        return bankAccountRepository.count();
    }
}