package fr.cpe.scoobygang.atelier2.security;

import fr.cpe.scoobygang.atelier2.model.User;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void isOkIsOk() {
        final User user = new User();
        user.setName("Rached");
        user.setPassword("123");
        user.setSurname("Mejri");

        String jwt = jwtService.fromUser(user).getToken();
        assertTrue(jwtService.isOk(jwt));
    }


    @Test
    void isOkIsNotOk() {
        String badJWT = "ccJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSYWNoZWQiLCJleHAiOjE3MTYyOTUwMjd9.VqEaepmMAIf5ySFFXDu09YsIlLqerCZMYmmeKK3AsQG8CHUJgroMrBFcgbFD4M4A1IO7hZOnFHvfHOdVjYzbOw";
        assertFalse(jwtService.isOk(badJWT));
    }
}