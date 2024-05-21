package fr.cpe.scoobygang.atelier1.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
@Builder
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

        return Card
                .builder()
                .name(name)
                .description(description)
                .family(family)
                .affinity(affinity)
                .imgUrl(imgUrl)
                .smallImgUrl(smallImgUrl)
                .id(id)
                .energy(energy)
                .hp(hp)
                .defence(defence)
                .attack(attack)
                .price(price)
                .userId(userId)
                .build();
    }

    public static Card fromDTO(CardFormDto cardFormDto) {
        return Card
                .builder()
                .affinity(cardFormDto.getAffinity())
                .attack(cardFormDto.getAttack())
                .hp(cardFormDto.getHp())
                .defence(cardFormDto.getDefence())
                .family(cardFormDto.getFamily())
                .name(cardFormDto.getName())
                .imgUrl(cardFormDto.getImgUrl())
                .energy(cardFormDto.getEnergy())
                .description(cardFormDto.getDescription())
                .build();
    }
}
