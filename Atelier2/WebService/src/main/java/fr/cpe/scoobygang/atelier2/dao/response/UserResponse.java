package fr.cpe.scoobygang.atelier2.dao.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
    private int id;
    private String username;
    private String surname;
    private String name;
    private String email;
    private double balance;
}

