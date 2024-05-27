package fr.cpe.scoobygang.atelier3.api_transaction_microservice.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("fr.cpe.scoobygang.common.model")
public class EntityScanDemo {
}
