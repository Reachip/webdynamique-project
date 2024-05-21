package fr.cpe.scoobygang.atelier2.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {
    private String name;
    private String surname;
    private String password;
}
