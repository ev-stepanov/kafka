package ru.company.kafka.usercassandra.repository;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.company.kafka.usercassandra.model.BankAccount;

import java.util.List;

public interface BankAccountRepository extends CassandraRepository<BankAccount, Long> {
    @AllowFiltering
    List<BankAccount> findByLastName(String lastName);
}
