package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.dao.request.LoginRequest;
import fr.cpe.scoobygang.atelier2.dao.request.StoreOrderRequest;
import fr.cpe.scoobygang.atelier2.dao.response.StoreResponse;
import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.repository.CardRepository;
import fr.cpe.scoobygang.atelier2.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreControllerTest {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreController storeController;

    @Autowired
    private UserController userController;

    @Autowired
    private CardRepository cardRepository;

    private String authorization;

    @BeforeEach
    public void setup() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("admin");
        loginRequest.setUsername("admin");

        this.authorization = "Bearer " + userController.login(loginRequest).getBody().get().getToken();
    }

    void getStores() {
        ResponseEntity<List<StoreResponse>> stores = storeController.getStores();
        assertEquals(200, stores.getStatusCode().value());
        // assertEquals(2, stores.getBody().size());
    }

    @Test
    void buyCard() {
        StoreOrderRequest storeOrderRequest = new StoreOrderRequest();
        storeOrderRequest.setCardId(1);
        storeOrderRequest.setStoreId(1);

        ResponseEntity<HttpStatus> stores = storeController.buyCard(this.authorization, storeOrderRequest);
        assertEquals(200, stores.getStatusCode().value());
    }

    @Test
    void sellCard() {
        Iterable<Card> cards = cardRepository.findByOwnerId(1);

        StoreOrderRequest storeOrderRequest = new StoreOrderRequest();

        Card firstUserCard = cards.iterator().next();

        storeOrderRequest.setCardId(firstUserCard.getId());
        storeOrderRequest.setStoreId(1);

        ResponseEntity<ResponseStatus> stores = storeController.sellCard(this.authorization, storeOrderRequest);
        assertEquals(401, stores.getStatusCode().value());
    }

    @Test
    void testSellCard() {
    }

    @Test
    void cancelSellCard() {
    }
}