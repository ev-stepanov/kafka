package ru.company.oracletarantoolloader.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tarantool.Iterator;
import org.tarantool.TarantoolClusterClient;
import ru.company.oracletarantoolloader.model.tarantool.BankAccountInfoTarantool;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TarantoolBankAccountRepository {
    private static final String SPACE_NAME = "bank_account_info";

    private final TarantoolClusterClient tarantoolClusterClient;

    public void save(BankAccountInfoTarantool bankAccountInfoTarantool) {
        tarantoolClusterClient.syncOps()
                .insert(SPACE_NAME, bankAccountInfoTarantool.asList());
    }

    public List<BankAccountInfoTarantool> findByCity(String city) {
        return tarantoolClusterClient.syncOps()
                .select(SPACE_NAME, "city_index", Collections.singletonList(city), 0, 10, Iterator.EQ)
                .stream()
                .map(o -> BankAccountInfoTarantool.convert((List<Object>) o))
                .peek(bankAccountInfoTarantool ->  log.info(bankAccountInfoTarantool.toString()))
                .collect(Collectors.toList());
    }

}
