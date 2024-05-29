package fr.cpe.scoobygang.atelier2.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void toUser() {
    }

    @Test
    void canBuyIsOK() {
        User user = new User();
        user.setBalance(10);

        assertTrue(user.canBuy(9));
    }

    @Test
    void canBuyNotOK() {
        User user = new User();
        user.setBalance(10);

        assertFalse(user.canBuy(12));
    }

    @Test
    void getId() {
        User user = new User();
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    void getUsername() {
        User user = new User();
        user.setUsername("testuser");
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void getSurname() {
        User user = new User();
        user.setSurname("TestSurname");
        assertEquals("TestSurname", user.getSurname());
    }

    @Test
    void getName() {
        User user = new User();
        user.setName("TestName");
        assertEquals("TestName", user.getName());
    }

    @Test
    void getEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void getPassword() {
        User user = new User();
        user.setPassword("testpassword");
        assertEquals("testpassword", user.getPassword());
    }

    @Test
    void getBalance() {
        User user = new User();
        user.setBalance(100.0);
        assertEquals(100.0, user.getBalance());
    }

    @Test
    void getCardList() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card());
        cards.add(new Card());

        User user = new User();
        user.setCardList(cards);

        assertEquals(cards, user.getCardList());
    }

    @Test
    void setId() {
        User user = new User();
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    void setUsername() {
        User user = new User();
        user.setUsername("testuser");
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void setSurname() {
        User user = new User();
        user.setSurname("TestSurname");
        assertEquals("TestSurname", user.getSurname());
    }

    @Test
    void setName() {
        User user = new User();
        user.setName("TestName");
        assertEquals("TestName", user.getName());
    }

    @Test
    void setEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void setPassword() {
        User user = new User();
        user.setPassword("testpassword");
        assertEquals("testpassword", user.getPassword());
    }

    @Test
    void setBalance() {
        User user = new User();
        user.setBalance(100.0);
        assertEquals(100.0, user.getBalance());
    }

    @Test
    void setCardList() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card());
        cards.add(new Card());

        User user = new User();
        user.setCardList(cards);

        assertEquals(cards, user.getCardList());
    }
}