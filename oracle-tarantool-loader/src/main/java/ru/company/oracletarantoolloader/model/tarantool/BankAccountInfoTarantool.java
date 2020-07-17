package ru.company.oracletarantoolloader.model.tarantool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountInfoTarantool {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String city;
    private Boolean blackListed;

    public static BankAccountInfoTarantool convert(List<Object> tuples) {
        return BankAccountInfoTarantool.builder()
                .uuid(UUID.fromString(tuples.get(0).toString()))
                .firstName(String.valueOf(tuples.get(1)))
                .lastName(String.valueOf(tuples.get(2)))
                .city(String.valueOf(tuples.get(3)))
                .blackListed(Boolean.parseBoolean(tuples.get(4).toString()))
                .build();
    }

    public List<?> asList() {
        return Arrays.asList(uuid.toString(), firstName, lastName, city, blackListed);
    }
}
