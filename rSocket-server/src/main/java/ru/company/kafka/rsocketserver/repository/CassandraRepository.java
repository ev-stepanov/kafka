package ru.company.kafka.rsocketserver.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import ru.company.kafka.rsocketserver.model.BankAccountInfo;

import java.util.UUID;

public interface CassandraRepository extends ReactiveCassandraRepository<BankAccountInfo, UUID> {
}
