package fr.cpe.scoobygang.atelier2.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String surname;
    private String password;
}
