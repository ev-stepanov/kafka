package ru.company.kafka.usercassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.company.kafka.usercassandra.model.BankAccountInfo;

import java.util.UUID;

public interface BankAccountRepository extends CassandraRepository<BankAccountInfo, UUID> {
}
