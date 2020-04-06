package ru.company.kafka.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.company.kafka.model.BankAccountInfo;

import java.util.UUID;

public interface BankAccountRepository extends CassandraRepository<BankAccountInfo, UUID> {
}
