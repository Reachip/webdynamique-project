package fr.cpe.scoobygang.atelier2.request;

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
    private String smallImgUrl;
    private int energy;
    private int hp;
    private int defence;
    private int attack;
    private int price;
    private int userId;
}
