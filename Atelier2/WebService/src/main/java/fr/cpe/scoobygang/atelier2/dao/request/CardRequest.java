package fr.cpe.scoobygang.atelier2.dao.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequest {
    private String name;
    private String description;
    private String family;
    private String affinity;
    private String imgUrl;
    private int energy;
    private int hp;
    private int defense;
    private int attack;
    private int price;
    private int userId;
}
