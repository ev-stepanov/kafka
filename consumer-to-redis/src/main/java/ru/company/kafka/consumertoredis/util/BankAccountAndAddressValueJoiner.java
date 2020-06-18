package ru.company.kafka.consumertoredis.util;

import org.apache.kafka.streams.kstream.ValueJoiner;
import ru.company.kafka.consumertoredis.model.Address;
import ru.company.kafka.consumertoredis.model.BankAccount;
import ru.company.kafka.consumertoredis.model.BankAccountInfo;

public class BankAccountAndAddressValueJoiner implements ValueJoiner<BankAccount, Address, BankAccountInfo> {

    @Override
    public BankAccountInfo apply(BankAccount bankAccount, Address address) {
        return new BankAccountInfo(bankAccount, address);
    }
}
