package fr.cpe.scoobygang.atelier2.security;

import fr.cpe.scoobygang.atelier2.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class JWT {
    private static final long EXPIRATION_TIME = 15;
    private static final String SECRET = "HRlELXqpSB";

    private String token;

    public static JWT fromUser(User user) {
        final String token = Jwts.builder()
                .setSubject(user.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000 * 60))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return new JWT(token);
    }

    public static boolean isOk(String jwt) {
        return false;
    }

    @Override
    public String toString() {
        return token;
    }
}
