package fr.cpe.scoobygang.common.resource;

import org.springframework.core.io.ClassPathResource;

public class CardResource implements Resource {
    @Override
    public org.springframework.core.io.Resource load() {
        return new ClassPathResource("./card.json");
    }
}
