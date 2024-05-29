package fr.cpe.scoobygang.atelier3.api_orchestrator_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiOrchestratorMicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiOrchestratorMicroserviceApplication.class, args);
	}

}
