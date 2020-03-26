package ru.company.kafka.addressgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AddressGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressGeneratorApplication.class, args);
	}

}
