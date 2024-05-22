package fr.cpe.scoobygang.atelier2.model;

import jakarta.persistence.*;
import lombok.*;
import org.json.JSONObject;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Integer lastUserId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User owner;

    public static Card toCard(JSONObject jsonObject) {
        Card card = new Card();

        card.setName(jsonObject.getString("name"));
        card.setDescription(jsonObject.getString("description"));
        card.setFamily(jsonObject.getString("family"));
        card.setAffinity(jsonObject.getString("affinity"));
        card.setImgUrl(jsonObject.getString("imgUrl"));
        card.setEnergy(jsonObject.getInt("energy"));
        card.setHp(jsonObject.getDouble("hp"));
        card.setDefense(jsonObject.getDouble("defense"));
        card.setAttack(jsonObject.getDouble("attack"));
        card.setPrice(jsonObject.getDouble("price"));

        return card;
    }
}
