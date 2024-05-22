package fr.cpe.scoobygang.atelier2.resource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class StoreResource implements Resource {
    @Override
    public org.springframework.core.io.Resource load() {
        return new ClassPathResource("./store.json");
    }
}
