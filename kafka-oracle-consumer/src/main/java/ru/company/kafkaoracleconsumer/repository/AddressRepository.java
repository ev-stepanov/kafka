package ru.company.kafkaoracleconsumer.repository;

import org.springframework.data.repository.CrudRepository;
import ru.company.kafkaoracleconsumer.model.Address;

import java.util.UUID;

public interface AddressRepository extends CrudRepository<Address, UUID> {
}
