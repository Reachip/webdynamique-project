package fr.cpe.scoobygang.atelier2.security;

import fr.cpe.scoobygang.atelier2.model.User;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JWTTest {
    @Test
    void fromUserIsOK() {
        final User user = new User();
        user.setName("Rached");
        user.setPassword("123");
        user.setSurname("Mejri");

        Assert.notNull(JWT.fromUser(user));
    }

    @Test
    void isOkIsOk() {
        final User user = new User();
        user.setName("Rached");
        user.setPassword("123");
        user.setSurname("Mejri");

        String jwt = JWT.fromUser(user).getToken();
        assertTrue(JWT.isOk(jwt));
    }


    @Test
    void isOkIsNotOk() {
        String badJWT = "ccJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSYWNoZWQiLCJleHAiOjE3MTYyOTUwMjd9.VqEaepmMAIf5ySFFXDu09YsIlLqerCZMYmmeKK3AsQG8CHUJgroMrBFcgbFD4M4A1IO7hZOnFHvfHOdVjYzbOw";
        assertFalse(JWT.isOk(badJWT));
    }
}