package fr.cpe.scoobygang.common.security.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JWTInformation {
    private int userID;
}
