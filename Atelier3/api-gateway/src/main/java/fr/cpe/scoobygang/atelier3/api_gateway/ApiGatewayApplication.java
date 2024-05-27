package fr.cpe.scoobygang.atelier3.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("user-route", r -> r.path("/user/**").uri("lb://user-service"))
				.route("card-route", r -> r.path("/card/**").uri("lb://card-service"))
				.route("store-route", r -> r.path("/store/**").uri("lb://store-service"))
				.route("transaction-route", r -> r.path("/transaction/**").uri("lb://transaction-service"))
				.build();
	}
}
