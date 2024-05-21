package fr.cpe.scoobygang.atelier2.security;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.Base64;

@Getter
@Setter
public class JWT {
    private final JWTInformation jwtInformation;
    private String token;

    public JWT(String token) {
        this.token = token;

        final Base64.Decoder decoder = Base64.getUrlDecoder();
        final String[] chunks = this.token.split("\\.");

        final String payload = new String(decoder.decode(chunks[1]));

        JSONObject jsonObject = new JSONObject(payload);

        this.jwtInformation =  JWTInformation.builder()
                .userID(Integer.parseInt(jsonObject.getString("sub")))
                .build();
    }

    @Override
    public String toString() {
        return token;
    }
}
