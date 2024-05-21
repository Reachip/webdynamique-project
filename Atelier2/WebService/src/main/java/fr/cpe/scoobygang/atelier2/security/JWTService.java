package fr.cpe.scoobygang.atelier2.security;

import fr.cpe.scoobygang.atelier2.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JWTService {
    private static final long EXPIRATION_TIME = 15;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public JWT fromUser(User user) {
        final String token = Jwts.builder()
                .setSubject(user.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000 * 60))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

        return new JWT(token);
    }

    public Optional<JWT> fromAuthorization(String authorizationString) {
        try {
            String token = authorizationString.split(" ")[1];
            JWT jwt = new JWT(token);

            if (isOk(jwt)) {
                return Optional.of(jwt);
            }

            return Optional.empty();
        } catch (Exception why) {
            return Optional.empty();
        }
    }

    private boolean isOk(JWT jwt) {
        try {
            Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(jwt.getToken())
                    .getBody();
            return true;
        } catch (Exception why) {
            return false;
        }
    }
}
