package fr.cpe.scoobygang.atelier3.api_store_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "fr.cpe.scoobygang.common.repository")
@ComponentScan(basePackages = {
		"fr.cpe.scoobygang.common.*",
		"fr.cpe.scoobygang.atelier3.api_store_microservice.*",
})
@EntityScan("fr.cpe.scoobygang.common.model")
public class ApiStoreMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiStoreMicroserviceApplication.class, args);
	}

}
