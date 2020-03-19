package ru.neoflex.kafka.produser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProduserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProduserApplication.class, args);
	}
}
