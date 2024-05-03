package fr.cpe.scoobygang.atelier1.model;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

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

    public static Card fromJsonObject(JSONObject jsonObject) {
        String name = jsonObject.isNull("name") ? "" : jsonObject.getString("name");
        String description = jsonObject.isNull("description") ?"" : jsonObject.getString("description");
        String family = jsonObject.isNull("family") ? "" :jsonObject.getString("family");
        String affinity = jsonObject.isNull("affinity") ? "" :jsonObject.getString("affinity");
        String imgUrl = jsonObject.isNull("imgUrl") ? "" :jsonObject.getString("imgUrl");
        String smallImgUrl = jsonObject.isNull("smallImgUrl") ?"" : jsonObject.getString("smallImgUrl");
        int id = jsonObject.isNull("id") ? 0 :jsonObject.getInt("id");
        int energy = jsonObject.isNull("energy") ? 0: jsonObject.getInt("energy");
        double hp = jsonObject.isNull("hp") ? 0:jsonObject.getDouble("hp");
        double defence = jsonObject.isNull("defence") ?0: jsonObject.getDouble("defence");
        double attack = jsonObject.isNull("attack") ? 0:jsonObject.getDouble("attack");
        double price = jsonObject.isNull("price") ? 0:jsonObject.getDouble("price");
        int userId = jsonObject.isNull("userId") ? 0 : jsonObject.getInt("userId");

        return new Card(name, description, family, affinity, imgUrl, smallImgUrl, id, energy, hp, defence, attack, price, userId);
    }
}
