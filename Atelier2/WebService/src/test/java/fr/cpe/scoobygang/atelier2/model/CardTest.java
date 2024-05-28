package fr.cpe.scoobygang.atelier2.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void toCard() throws JSONException {
        String json = "{\"id\": 2, \"price\": 10.1, \"attack\": 10.1, \"defense\": 10.2, \"hp\": 10.0, \"energy\": 10, \"imgUrl\": \"image url\", \"affinity\": \"affinity 1\", \"family\": \"family 1\", \"name\": \"Lenny's Card\", \"description\": \"description\"}";
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

    @Test
    void getId() {
        Card card = new Card();
        card.setId(1);
        assertEquals(1, card.getId());
    }

    @Test
    void getName() {
        Card card = new Card();
        card.setName("Test Name");
        assertEquals("Test Name", card.getName());
    }

    @Test
    void getDescription() {
        Card card = new Card();
        card.setDescription("Test Description");
        assertEquals("Test Description", card.getDescription());
    }

    @Test
    void getFamily() {
        Card card = new Card();
        card.setFamily("Test Family");
        assertEquals("Test Family", card.getFamily());
    }

    @Test
    void getAffinity() {
        Card card = new Card();
        card.setAffinity("Test Affinity");
        assertEquals("Test Affinity", card.getAffinity());
    }

    @Test
    void getImgUrl() {
        Card card = new Card();
        card.setImgUrl("Test Image URL");
        assertEquals("Test Image URL", card.getImgUrl());
    }

    @Test
    void getEnergy() {
        Card card = new Card();
        card.setEnergy(100);
        assertEquals(100, card.getEnergy());
    }

    @Test
    void getHp() {
        Card card = new Card();
        card.setHp(100.0);
        assertEquals(100.0, card.getHp());
    }

    @Test
    void getDefense() {
        Card card = new Card();
        card.setDefense(50.0);
        assertEquals(50.0, card.getDefense());
    }

    @Test
    void getAttack() {
        Card card = new Card();
        card.setAttack(75.0);
        assertEquals(75.0, card.getAttack());
    }

    @Test
    void getPrice() {
        Card card = new Card();
        card.setPrice(99.99);
        assertEquals(99.99, card.getPrice());
    }

    @Test
    void getOwner() {
        User owner = new User();
        Card card = new Card();
        card.setOwner(owner);
        assertEquals(owner, card.getOwner());
    }

    @Test
    void getStore() {
        Store store = new Store();
        Card card = new Card();
        card.setStore(store);
        assertEquals(store, card.getStore());
    }

    @Test
    void setId() {
        Card card = new Card();
        card.setId(1);
        assertEquals(1, card.getId());
    }

    @Test
    void setName() {
        Card card = new Card();
        card.setName("Test Name");
        assertEquals("Test Name", card.getName());
    }

    @Test
    void setDescription() {
        Card card = new Card();
        card.setDescription("Test Description");
        assertEquals("Test Description", card.getDescription());
    }

    @Test
    void setFamily() {
        Card card = new Card();
        card.setFamily("Test Family");
        assertEquals("Test Family", card.getFamily());
    }

    @Test
    void setAffinity() {
        Card card = new Card();
        card.setAffinity("Test Affinity");
        assertEquals("Test Affinity", card.getAffinity());
    }

    @Test
    void setImgUrl() {
        Card card = new Card();
        card.setImgUrl("Test Image URL");
        assertEquals("Test Image URL", card.getImgUrl());
    }

    @Test
    void setEnergy() {
        Card card = new Card();
        card.setEnergy(100);
        assertEquals(100, card.getEnergy());
    }

    @Test
    void setHp() {
        Card card = new Card();
        card.setHp(100.0);
        assertEquals(100.0, card.getHp());
    }

    @Test
    void setDefense() {
        Card card = new Card();
        card.setDefense(50.0);
        assertEquals(50.0, card.getDefense());
    }

    @Test
    void setAttack() {
        Card card = new Card();
        card.setAttack(75.0);
        assertEquals(75.0, card.getAttack());
    }

    @Test
    void setPrice() {
        Card card = new Card();
        card.setPrice(99.99);
        assertEquals(99.99, card.getPrice());
    }

    @Test
    void setOwner() {
        User owner = new User();
        Card card = new Card();
        card.setOwner(owner);
        assertEquals(owner, card.getOwner());
    }

    @Test
    void setStore() {
        Store store = new Store();
        Card card = new Card();
        card.setStore(store);
        assertEquals(store, card.getStore());
    }
}