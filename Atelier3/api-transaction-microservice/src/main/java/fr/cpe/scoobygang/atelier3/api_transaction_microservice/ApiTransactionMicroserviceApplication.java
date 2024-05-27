package fr.cpe.scoobygang.atelier3.api_transaction_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"fr.cpe.scoobygang.common.jwt",
		"fr.cpe.scoobygang.common.resource",
		"fr.cpe.scoobygang.common.repository",
		"fr.cpe.scoobygang.common.config"
})
@EntityScan("fr.cpe.scoobygang.common.model")
public class ApiTransactionMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTransactionMicroserviceApplication.class, args);
	}

}
