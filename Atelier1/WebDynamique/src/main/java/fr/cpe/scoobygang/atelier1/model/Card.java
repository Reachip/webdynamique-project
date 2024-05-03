package fr.cpe.scoobygang.atelier1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Card {
    private String name;
    private String description;
    private String family;
    private String affinity;
    private String imgUrl;
    private String smallImgUrl;
    private int id;
    private int energy;
    private float hp;
    private float defence;
    private float attack;
    private float price;
    private int userId;
}
