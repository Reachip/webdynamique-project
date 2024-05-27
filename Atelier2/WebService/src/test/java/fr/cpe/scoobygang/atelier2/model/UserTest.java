package fr.cpe.scoobygang.atelier2.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

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
}