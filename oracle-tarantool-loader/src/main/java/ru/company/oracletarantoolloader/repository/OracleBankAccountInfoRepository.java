package ru.company.oracletarantoolloader.repository;

import org.springframework.data.repository.CrudRepository;
import ru.company.oracletarantoolloader.model.oracle.BankAccountInfoOracle;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OracleBankAccountInfoRepository extends CrudRepository<BankAccountInfoOracle, UUID> {
    List<BankAccountInfoOracle> findAllByCreatedDateBetween(Date start, Date end);

}
