package fr.cpe.scoobygang.atelier2.security;

import fr.cpe.scoobygang.atelier2.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JWTServiceTest {
    @Autowired
    private JWTService jwtService;

    @Test
    void fromUserIsOK() {
        User user = new User();
        user.setId(10);

        JWT jwt = jwtService.fromUser(user);
        assertEquals(user.getId(), jwt.getJwtInformation().getUserID());
    }

    @Test
    void fromAuthorizationIsOK() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        Optional<JWT> jwt = jwtService.fromAuthorization("Barer " + token);

        assertTrue(jwt.isPresent());
        assertEquals(1234567890, jwt.get().getJwtInformation().getUserID());
    }

    @Test
    void fromAuthorizationIsNotOKBecauseTokenIsIncorrect() {
        Optional<JWT> jwt = jwtService.fromAuthorization("Barer null");
        assertTrue(jwt.isEmpty());
    }

    @Test
    void fromAuthorizationIsNotOKBecauseAuthorizationIsIncorrect() {
        Optional<JWT> jwt = jwtService.fromAuthorization("123");
        assertTrue(jwt.isEmpty());
    }
}