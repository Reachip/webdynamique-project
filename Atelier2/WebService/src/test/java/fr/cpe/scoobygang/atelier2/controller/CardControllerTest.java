package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.dao.request.CardRequest;
import fr.cpe.scoobygang.atelier2.dao.request.LoginRequest;
import fr.cpe.scoobygang.atelier2.dao.response.CardResponse;
import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.repository.CardRepository;
import fr.cpe.scoobygang.atelier2.security.JWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardControllerTest {
    @Autowired
    private CardController cardController;

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

    @Test
    void deleteCard() {
        Card cardToDelete = cardRepository.findById(1).get();
        cardController.deleteCard(this.authorization, cardToDelete.getId());
        assertTrue(cardRepository.findById(1).isEmpty());
    }

    @Test
    void createCard() {
        CardRequest cardRequest = new CardRequest();
        cardRequest.setName("Jonny's card");

       ResponseEntity<Card> createdCard = cardController.createCard(this.authorization, cardRequest);

       assertEquals(201, createdCard.getStatusCode().value());

       Card body = createdCard.getBody();

       assertEquals("Jonny's card", body.getName());
       assertEquals("Jonny's card", cardRepository.findById(body.getId()).get().getName());
    }

    @Test
    void updateCard() {
        Card card = new Card();
        card.setName("Jonny's card");
        card = cardRepository.save(card);

        CardRequest cardRequest  = new CardRequest();
        cardRequest.setName("Paul's card");

        cardController.updateCard(this.authorization, card.getId(), cardRequest);
        assertEquals("Paul's card", cardRepository.findById(card.getId()).get().getName());
    }

    @Test
    void getCard() {
        Card card = cardRepository.findAll().iterator().next();
        ResponseEntity<Card> cardRequest = cardController.getCard(authorization, card.getId());



        assertEquals(200, cardRequest.getStatusCode().value());
        assertEquals(card.getName(), cardRequest.getBody().getName());
    }

    @Test
    void getCards() {
        ResponseEntity<List<CardResponse>> cards = cardController.getCards(this.authorization);
        List<CardResponse> response = cards.getBody();

        assertEquals(200, cards.getStatusCode().value());
        assertEquals(57, response.size());
    }

    @Test
    void getCurrentUserCards() {
        ResponseEntity<List<CardResponse>> cards = cardController.getCurrentUserCards(this.authorization);
        List<CardResponse> response = cards.getBody();

        assertEquals(200, cards.getStatusCode().value());

        assert response != null;
        assertFalse(response.isEmpty());
    }
}