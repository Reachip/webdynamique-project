package fr.cpe.scoobygang.atelier2.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class JWTTest {
    @Test
    public void shouldConstructToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        JWT jwt = new JWT(token);

        assertEquals(token, jwt.getToken());
        assertEquals(1234567890, jwt.getJwtInformation().getUserID());
    }
}