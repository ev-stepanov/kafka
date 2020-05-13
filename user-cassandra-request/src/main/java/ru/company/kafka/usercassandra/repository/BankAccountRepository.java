package ru.company.kafka.usercassandra.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.company.kafka.bankaccountredismodel.BankAccountInfo;

public interface BankAccountRepository extends KeyValueRepository<BankAccountInfo, String> {
}
