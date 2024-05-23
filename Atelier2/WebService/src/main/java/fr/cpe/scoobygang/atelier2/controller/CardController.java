package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.runner.CardApplicationRunner;
import fr.cpe.scoobygang.atelier2.mapper.CardMapper;
import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.request.CardRequest;
import fr.cpe.scoobygang.atelier2.request.CardResponse;
import fr.cpe.scoobygang.atelier2.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class CardController {
    private final CardService cardService;
    private final CardApplicationRunner cardApplicationRunner;

    public CardController(CardService cardService, CardApplicationRunner cardApplicationRunner) {
        this.cardService = cardService;
        this.cardApplicationRunner = cardApplicationRunner;
    }

    @DeleteMapping(value = {"/card/{id}"})
    public ResponseEntity<Void> deleteCard(@PathVariable("id") int id, @RequestBody CardRequest cardRequest) {
        Optional<Card> card = cardService.deleteCard(id);
        if (card.isPresent()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PostMapping(value = {"/card"})
    public ResponseEntity<Card> createCard(@RequestBody CardRequest cardRequest) {
        Card createdCard = cardService.saveCard(cardRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCard.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdCard);
    }


    @PutMapping(value = {"/card/{id}"})
    public ResponseEntity<Card> updateCard(@PathVariable("id") int id, @RequestBody CardRequest cardRequest) {
        Optional<Card> updatedCard = cardService.updateCard(id, cardRequest);
        return updatedCard.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = {"/card/{id}"})
    public ResponseEntity<Card> getCard(@PathVariable("id") int id) {
        return cardService.getCard(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = {"/cards"})
    public ResponseEntity<List<CardResponse>> getCards() {
        return ResponseEntity.ok(CardMapper.INSTANCE.cardsToCardResponses(cardService.getCards()));
    }

    @GetMapping(value = {"/cards/user/{id}"})
    public ResponseEntity<List<CardResponse>> getCards(@PathVariable("id") int id) {
        return ResponseEntity.ok(CardMapper.INSTANCE.cardsToCardResponses(cardService.getAllUserCard(id)));
    }
}
