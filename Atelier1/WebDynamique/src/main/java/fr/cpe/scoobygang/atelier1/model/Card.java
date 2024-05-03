package fr.cpe.scoobygang.atelier1.model;

import jakarta.annotation.Nullable;
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
    private double hp;
    private double defence;
    private double attack;
    private double price;
    private Integer userId;
}
