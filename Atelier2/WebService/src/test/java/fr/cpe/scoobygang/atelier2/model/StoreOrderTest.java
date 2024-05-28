package fr.cpe.scoobygang.atelier2.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreOrderTest {

    @Test
    void getId() {
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setId(1);
        assertEquals(1, storeOrder.getId());
    }

    @Test
    void getOwner() {
        User owner = new User();
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setOwner(owner);
        assertEquals(owner, storeOrder.getOwner());
    }

    @Test
    void getCard() {
        Card card = new Card();
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setCard(card);
        assertEquals(card, storeOrder.getCard());
    }

    @Test
    void getStore() {
        Store store = new Store();
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setStore(store);
        assertEquals(store, storeOrder.getStore());
    }

    @Test
    void setId() {
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setId(1);
        assertEquals(1, storeOrder.getId());
    }

    @Test
    void setOwner() {
        User owner = new User();
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setOwner(owner);
        assertEquals(owner, storeOrder.getOwner());
    }

    @Test
    void setCard() {
        Card card = new Card();
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setCard(card);
        assertEquals(card, storeOrder.getCard());
    }

    @Test
    void setStore() {
        Store store = new Store();
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setStore(store);
        assertEquals(store, storeOrder.getStore());
    }
}