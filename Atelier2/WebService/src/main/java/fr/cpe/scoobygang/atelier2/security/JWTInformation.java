package fr.cpe.scoobygang.atelier2.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JWTInformation {
    private int userID;
}
