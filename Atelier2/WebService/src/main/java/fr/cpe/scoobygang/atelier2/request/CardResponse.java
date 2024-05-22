package fr.cpe.scoobygang.atelier2.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponse {
    private int id;
    private String name;
    private String description;
    private String family;
    private String affinity;
    private String imgUrl;
    private int energy;
    private double hp;
    private double defense;
    private double attack;
    private double price;
    private boolean isOnSale;
    private int userId;
    private int storeId;

}
