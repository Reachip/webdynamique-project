package fr.cpe.scoobygang.atelier3.api_store_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"fr.cpe.scoobygang.common.jwt"})
@EntityScan("fr.cpe.scoobygang.common.model")
public class ApiStoreMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiStoreMicroserviceApplication.class, args);
	}

}
