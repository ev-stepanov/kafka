package ru.company.kafka.addressgenerator.service;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;
import ru.company.kafka.model.producer.AddressDto;

import java.util.UUID;

@Service
public class AddressGeneratorService {
    private final Faker faker;

    public AddressGeneratorService(Faker faker) {
        this.faker = faker;
    }

    public AddressDto generateAddress(UUID uuid) {
        return AddressDto.builder()
                .uuid(uuid)
                .city(faker.address().city())
                .state(faker.address().state())
                .street(faker.address().streetAddress())
                .build();
    }
}
