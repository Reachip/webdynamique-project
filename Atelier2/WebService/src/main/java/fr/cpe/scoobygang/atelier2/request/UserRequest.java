package fr.cpe.scoobygang.atelier2.request;

import fr.cpe.scoobygang.atelier2.model.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class UserRequest {
    private int id;
    private String username;
    private String password;
    private String surname;
    private String name;
    private String email;
    private double balance = 0;
    private List<Card> cardList = new ArrayList<>();
}
