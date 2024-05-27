package fr.cpe.scoobygang.atelier3.api_card_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
		"fr.cpe.scoobygang.common.jwt",
		"fr.cpe.scoobygang.common.resource",
		"fr.cpe.scoobygang.common.repository",
		"fr.cpe.scoobygang.common.config"
})
@EntityScan("fr.cpe.scoobygang.common.model")
public class ApiCardMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCardMicroserviceApplication.class, args);
	}

}
