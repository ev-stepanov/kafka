package ru.company.kafka.consumer.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.company.kafka.consumer.model.Account;

import java.util.UUID;

public interface AccountRepository extends CassandraRepository<Account, UUID> {
}
