package fr.cpe.scoobygang.atelier2.security;

import fr.cpe.scoobygang.atelier2.model.User;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JWTTest {
    @Autowired
    private JWTService jwtService;

    @Test
    void fromUserIsOK() {
        final User user = new User();
        user.setName("Rached");
        user.setPassword("123");
        user.setSurname("Mejri");

        Assert.notNull(jwtService.fromUser(user));
    }

    @Test
    void fromAuthorizationIsNotOk() {
        Optional<JWT> jwt = jwtService.fromAuthorization("Bearer <TOKEN>");
        assertFalse(jwt.isPresent());
    }

    @Test
    void fromAuthorizationIsOk() {
        final User user = new User();
        user.setName("Rached");
        user.setPassword("123");
        user.setSurname("Mejri");

        JWT jwt = jwtService.fromUser(user);
        Optional<JWT> givenJWT = jwtService.fromAuthorization("Bearer " + jwt.toString());
        assertTrue(givenJWT.isPresent());
    }
}