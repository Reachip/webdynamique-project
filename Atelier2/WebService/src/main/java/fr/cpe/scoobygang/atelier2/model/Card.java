package fr.cpe.scoobygang.atelier2.model;

import jakarta.persistence.*;
import lombok.*;
import org.json.JSONObject;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    private String family;
    private String affinity;
    private String imgUrl;
    private String smallImgUrl;
    private int energy;
    private double hp;
    private double defence;
    private double attack;
    private double price;
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User owner;
}
