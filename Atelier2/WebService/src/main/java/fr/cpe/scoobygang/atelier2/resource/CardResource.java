package fr.cpe.scoobygang.atelier2.resource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class CardResource implements Resource {
    @Override
    public org.springframework.core.io.Resource load() {
        return new ClassPathResource("./card.json");
    }
}
