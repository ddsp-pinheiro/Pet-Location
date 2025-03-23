package com.pet_location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PetLocationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetLocationApplication.class, args);
	}

}
