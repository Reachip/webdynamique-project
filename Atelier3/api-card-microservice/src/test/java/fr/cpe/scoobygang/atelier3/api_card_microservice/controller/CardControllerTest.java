package fr.cpe.scoobygang.atelier3.api_card_microservice.controller;


import fr.cpe.scoobygang.atelier3.api_card_microservice.repository.CardRepository;
import fr.cpe.scoobygang.common.dto.request.LoginRequest;
import fr.cpe.scoobygang.common.dto.request.UserRequest;
import fr.cpe.scoobygang.common.jwt.JWT;
import fr.cpe.scoobygang.common.model.Card;
import fr.cpe.scoobygang.common.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JWT> loginResponse = restTemplate.postForEntity("http://localhost:8085/user/login", loginRequest, JWT.class);

        if (!loginResponse.getStatusCode().is2xxSuccessful()) return ;

        this.authorization = "Bearer " + loginResponse.getBody().getToken();
    }

    @Test
    void deleteCard() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity request = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Card> cardResponse =  restTemplate.getForEntity("http://localhost:8086/card/card/1", request, Card.class);

        if (!cardResponse.getStatusCode().is2xxSuccessful()) return ;

        Card cardToDelete = cardResponse.getBody();

        // Delete
        restTemplate.exchange("http://localhost:8086/card/card/"+cardToDelete.getId(), HttpMethod.DELETE, request, Void.class);

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