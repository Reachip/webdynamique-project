package fr.cpe.scoobygang.atelier1.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardFormDto {
    private String name;
    private String description;
    private String imgUrl;
    private String family;
    private String affinity;
    private int hp;
    private int energy;
    private int attack;
    private int defence;


    public CardFormDto() {
        this.name = "";
        this.description = "";
        this.imgUrl = "";
        this.family = "";
        this.affinity = "";
        this.hp = 0;
        this.energy = 0;
        this.attack = 0;
        this.defence = 0;
    }

    public CardFormDto(String name, String description, String imgUrl, String family, String affinity, int hp, int energy, int attack, int defence)
    {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.family = family;
        this.affinity = affinity;
        this.hp = hp;
        this.energy = energy;
        this.attack = attack;
        this.defence = defence;
    }
}
