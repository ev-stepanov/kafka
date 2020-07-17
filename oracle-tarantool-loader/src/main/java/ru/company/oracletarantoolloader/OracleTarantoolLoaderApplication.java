package ru.company.oracletarantoolloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OracleTarantoolLoaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OracleTarantoolLoaderApplication.class, args);
	}

}
