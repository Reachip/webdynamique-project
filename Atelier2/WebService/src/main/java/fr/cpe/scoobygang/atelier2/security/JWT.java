package fr.cpe.scoobygang.atelier2.security;

import fr.cpe.scoobygang.atelier2.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.crypto.spec.SecretKeySpec;

@Getter
@Setter
@AllArgsConstructor
public class JWT {
    private String token;

    public static JWT fromUser(User user) {
        String token = Jwts.builder()
                .setSubject(user.getName())
                .signWith(new SecretKeySpec((user.getName() + "Bar12345Bar12345"+"Bar12345Bar12345"+"Bar12345Bar12345").getBytes(), "AES"))
                .compact();

        return new JWT(token);
    }

    @Override
    public String toString() {
        return token;
    }
}
