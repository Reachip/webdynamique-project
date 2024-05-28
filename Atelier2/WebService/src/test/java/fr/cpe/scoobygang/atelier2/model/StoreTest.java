package fr.cpe.scoobygang.atelier2.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

    @Test
    void toStore() throws JSONException {
        String json = "{\"id\": 2, \"name\": \"Lenny's Card\"}";
        Store store = Store.toStore(new JSONObject(json));

        assertEquals("Lenny's Card", store.getName());
    }

    @Test
    void getId() {
        Store store = new Store();
        store.setId(1);
        assertEquals(1, store.getId());
    }

    @Test
    void getName() {
        Store store = new Store();
        store.setName("Test Store");
        assertEquals("Test Store", store.getName());
    }

    @Test
    void getCardList() {
        List<Card> cardList = new ArrayList<>();
        Store store = new Store();
        store.setCardList(cardList);
        assertEquals(cardList, store.getCardList());
    }

    @Test
    void setId() {
        Store store = new Store();
        store.setId(1);
        assertEquals(1, store.getId());
    }

    @Test
    void setName() {
        Store store = new Store();
        store.setName("Test Store");
        assertEquals("Test Store", store.getName());
    }

    @Test
    void setCardList() {
        List<Card> cardList = new ArrayList<>();
        Store store = new Store();
        store.setCardList(cardList);
        assertEquals(cardList, store.getCardList());
    }
}