package fr.cpe.scoobygang.common.security.resource;

import org.springframework.core.io.ClassPathResource;

public class UserResource implements Resource {
    @Override
    public org.springframework.core.io.Resource load() {
        return new ClassPathResource("./user.json");
    }
}
