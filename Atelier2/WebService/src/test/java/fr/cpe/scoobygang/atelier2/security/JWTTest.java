package fr.cpe.scoobygang.atelier2.security;

import fr.cpe.scoobygang.atelier2.model.User;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JWTTest {
    @Test
    void fromUserIsOK() {
        User user = new User();
        user.setName("Rached");
        user.setPassword("123");
        user.setSurname("Mejri");

        Assert.notNull(JWT.fromUser(user));
    }
}