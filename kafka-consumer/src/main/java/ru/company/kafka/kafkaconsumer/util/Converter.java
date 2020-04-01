package ru.company.kafka.kafkaconsumer.util;

import ru.company.kafka.kafkaconsumer.model.BankAccountInfo;
import ru.company.kafka.model.producer.AddressDto;
import ru.company.kafka.model.producer.BankAccountDto;

public class Converter {
    public static BankAccountInfo addressAndAccountToBankAccountInfo(BankAccountDto bankAccountDto, AddressDto addressDto) {
        return BankAccountInfo.builder()
                .uuid(addressDto.getUuid())
                .firstName(bankAccountDto.getFirstName())
                .lastName(bankAccountDto.getLastName())
                .birthday(bankAccountDto.getBirthday())
                .balance(bankAccountDto.getBalance())
                .typeAccount(bankAccountDto.getTypeAccount())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .numberOfHome(addressDto.getNumberOfHome())
                .build();
    }

}
