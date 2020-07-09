package ru.company.kafkaoracleconsumer.util;

import ru.company.kafkaoracleconsumer.dto.AddressDto;
import ru.company.kafkaoracleconsumer.dto.BankAccountDto;
import ru.company.kafkaoracleconsumer.model.Address;
import ru.company.kafkaoracleconsumer.model.BankAccount;

public class ConverterDto {
    public static BankAccount convertToBankAccountFrom(BankAccountDto dto) {
        return BankAccount.builder()
                .uuid(dto.getUuid())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .balance(dto.getBalance())
                .birthday(dto.getBirthday())
                .typeAccount(BankAccount.TypeAccount.valueOf(dto.getTypeAccount().name()))
                .build();
    }

    public static Address convertToAddressFrom(AddressDto dto) {
        return Address.builder()
                .city(dto.getCity())
                .state(dto.getState())
                .street(dto.getStreet())
                .build();
    }
}
