package ru.company.userredisrequest.util;

import ru.company.userredisrequest.dto.BankAccountInfoSaveDto;
import ru.company.userredisrequest.exception.BadAccountTypeException;
import ru.company.userredisrequest.model.Address;
import ru.company.userredisrequest.model.BankAccount;
import ru.company.userredisrequest.model.BankAccountInfo;
import ru.company.userredisrequest.model.TypeAccount;

import java.util.UUID;

public class DtoConverter {
    private DtoConverter() {
        throw new IllegalStateException("Utility class");
    }
    public static BankAccountInfo bankAccountInfoSaveDtoConverter(BankAccountInfoSaveDto dto) {
        String uuid = UUID.randomUUID().toString();
        TypeAccount typeAccount;
        try {
            typeAccount = TypeAccount.valueOf(dto.getTypeAccount().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadAccountTypeException(e.getMessage());
        }
        return BankAccountInfo.builder()
                .uuid(uuid)
                .address(Address.builder()
                        .city(dto.getCity())
                        .state(dto.getState())
                        .street(dto.getStreet())
                        .build())
                .bankAccount(BankAccount.builder()
                        .balance(dto.getBalance())
                        .birthday(dto.getBirthday())
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .typeAccount(typeAccount)
                        .uuid(UUID.fromString(uuid))
                        .build())
                .build();
    }
}
