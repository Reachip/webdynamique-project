package fr.cpe.scoobygang.atelier2.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

    @Test
    void toStoreIsOk() throws JSONException {
        String json = "{id: 2, name: \"Lenny's Card\"}";
        Store card = Store.toStore(new JSONObject(json));

        assertEquals("Lenny's Card", card.getName());
    }
}