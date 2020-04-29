package ru.company.kafka.kafkaconsumer.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.company.kafka.kafkaconsumer.model.BankAccountInfo;

public interface AccountRepository extends KeyValueRepository<BankAccountInfo, String> {
}
