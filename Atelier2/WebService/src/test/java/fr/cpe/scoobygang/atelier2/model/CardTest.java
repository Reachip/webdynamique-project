package fr.cpe.scoobygang.atelier2.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void toCardIsOk() throws JSONException {
        String json = "{id: 2, price: 10.1, attack: 10.1, defense: 10.2, hp: 10.0, energy: 10, imgUrl: \"image url\", affinity: \"affinity 1\", family: \"family 1\", name: \"Lenny's Card\", description: \"\"}";
        Card card = Card.toCard(new JSONObject(json));

        assertEquals(card.getAffinity(), "affinity 1");
        assertEquals(card.getHp(), 10.0);
        assertEquals(card.getPrice(), 10.1);
        assertEquals(card.getAttack(), 10.1);
        assertEquals(card.getEnergy(), 10);
    }
}