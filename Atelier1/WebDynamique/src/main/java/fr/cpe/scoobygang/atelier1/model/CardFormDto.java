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
}
