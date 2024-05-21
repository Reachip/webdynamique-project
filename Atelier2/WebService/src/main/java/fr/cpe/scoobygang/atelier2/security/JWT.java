package fr.cpe.scoobygang.atelier2.security;

import fr.cpe.scoobygang.atelier2.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class JWT {
    private String token;

    @Override
    public String toString() {
        return token;
    }
}
