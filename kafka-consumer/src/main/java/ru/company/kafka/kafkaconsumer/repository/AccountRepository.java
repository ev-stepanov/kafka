package ru.company.kafka.kafkaconsumer.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.company.kafka.kafkaconsumer.model.BankAccountInfo;

import java.util.UUID;

public interface AccountRepository extends CassandraRepository<BankAccountInfo, UUID> {

}
