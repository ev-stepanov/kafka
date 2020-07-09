package ru.company.kafkaoracleconsumer.repository;

import org.springframework.data.repository.CrudRepository;
import ru.company.kafkaoracleconsumer.model.BankAccount;

import java.util.UUID;

public interface BankAccountRepository extends CrudRepository<BankAccount, UUID> {
}
