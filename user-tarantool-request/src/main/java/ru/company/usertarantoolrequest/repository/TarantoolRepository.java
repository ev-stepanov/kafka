package ru.company.usertarantoolrequest.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tarantool.Iterator;
import org.tarantool.TarantoolClusterClient;
import ru.company.usertarantoolrequest.model.BankAccountInfoTarantool;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TarantoolRepository {
    private static final String SPACE_NAME = "bank_account_info";
    private final TarantoolClusterClient tarantoolClusterClient;

    public List<BankAccountInfoTarantool> findByFirstAndLastNamesAndCity(String firstName, String lastName, String city) {
        return tarantoolClusterClient.syncOps()
                .select(SPACE_NAME, "FIO_AND_CITY_INDEX", List.of(firstName, lastName, city), 0, 10, Iterator.EQ)
                .stream()
                .map(o -> BankAccountInfoTarantool.convert((List<Object>) o))
                .peek(bankAccountInfoTarantool ->  log.info(bankAccountInfoTarantool.toString()))
                .collect(Collectors.toList());
    }
}
