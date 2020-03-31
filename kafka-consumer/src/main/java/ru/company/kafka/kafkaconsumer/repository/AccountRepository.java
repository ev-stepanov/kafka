package ru.company.kafka.kafkaconsumer.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ru.company.kafka.kafkaconsumer.model.AccountWithAdditionalInfo;

import java.util.UUID;

//@Repository
public interface AccountRepository extends CassandraRepository<AccountWithAdditionalInfo, UUID> {

}
