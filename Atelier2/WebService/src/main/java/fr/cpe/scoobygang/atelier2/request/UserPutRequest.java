package fr.cpe.scoobygang.atelier2.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPutRequest {
    private String username;
    private String oldPassword;
    private String password;
    private String surName;
    private String lastName;
    private String email;
    private double account;
}
