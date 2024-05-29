package fr.cpe.scoobygang.atelier2.model;

import org.junit.jupiter.api.Test;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void getId() {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        assertEquals(1, transaction.getId());
    }

    @Test
    void getOwner() {
        User owner = new User();
        Transaction transaction = new Transaction();
        transaction.setOwner(owner);
        assertEquals(owner, transaction.getOwner());
    }

    @Test
    void getCard() {
        Card card = new Card();
        Transaction transaction = new Transaction();
        transaction.setCard(card);
        assertEquals(card, transaction.getCard());
    }

    @Test
    void getStore() {
        Store store = new Store();
        Transaction transaction = new Transaction();
        transaction.setStore(store);
        assertEquals(store, transaction.getStore());
    }

    @Test
    void getAction() {
        TransactionAction action = TransactionAction.BUY;
        Transaction transaction = new Transaction();
        transaction.setAction(action);
        assertEquals(action, transaction.getAction());
    }

    @Test
    void getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Transaction transaction = new Transaction();
        transaction.setTimestamp(timestamp);
        assertEquals(timestamp, transaction.getTimestamp());
    }

    @Test
    void getAmount() {
        double amount = 99.99;
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        assertEquals(amount, transaction.getAmount());
    }

    @Test
    void setId() {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        assertEquals(1, transaction.getId());
    }

    @Test
    void setOwner() {
        User owner = new User();
        Transaction transaction = new Transaction();
        transaction.setOwner(owner);
        assertEquals(owner, transaction.getOwner());
    }

    @Test
    void setCard() {
        Card card = new Card();
        Transaction transaction = new Transaction();
        transaction.setCard(card);
        assertEquals(card, transaction.getCard());
    }

    @Test
    void setStore() {
        Store store = new Store();
        Transaction transaction = new Transaction();
        transaction.setStore(store);
        assertEquals(store, transaction.getStore());
    }

    @Test
    void setAction() {
        TransactionAction action = TransactionAction.BUY;
        Transaction transaction = new Transaction();
        transaction.setAction(action);
        assertEquals(action, transaction.getAction());
    }

    @Test
    void setTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Transaction transaction = new Transaction();
        transaction.setTimestamp(timestamp);
        assertEquals(timestamp, transaction.getTimestamp());
    }

    @Test
    void setAmount() {
        double amount = 99.99;
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        assertEquals(amount, transaction.getAmount());
    }
}