package fr.cpe.scoobygang.atelier3.api_orchestrator_microservice.configuration;

import com.netflix.discovery.shared.transport.jersey3.Jersey3TransportClientFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Jersey3TransportClientFactoriesConfig {
    @Bean
    public Jersey3TransportClientFactories jersey3TransportClientFactories() {
        return new Jersey3TransportClientFactories();
    }

}