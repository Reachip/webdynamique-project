package fr.cpe.scoobygang.atelier2.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    void toCardIsOk() throws JSONException {
        String json = "{id: 2, price: 10.1, attack: 10.1, defense: 10.2, hp: 10.0, energy: 10, imgUrl: \"image url\", affinity: \"affinity 1\", family: \"family 1\", name: \"Lenny's Card\", description: \"description\"}";
        Card card = Card.toCard(new JSONObject(json));

        assertEquals("affinity 1", card.getAffinity());
        assertEquals(10.0, card.getHp());
        assertEquals(10.1, card.getPrice());
        assertEquals(10.1, card.getAttack());
        assertEquals(10, card.getEnergy());
        assertEquals("image url", card.getImgUrl());
        assertEquals("family 1", card.getFamily());
        assertEquals("Lenny's Card", card.getName());
        assertEquals("description", card.getDescription());
    }
}