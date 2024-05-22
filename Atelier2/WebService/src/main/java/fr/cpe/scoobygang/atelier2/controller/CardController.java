package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.request.CardRequest;
import fr.cpe.scoobygang.atelier2.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @DeleteMapping(value = {"/card/{id}"})
    public ResponseEntity<Optional<Card>> deleteCard(@PathVariable("id") int id, @RequestBody CardRequest cardRequest) {
        return ResponseEntity.ok(cardService.deleteCard(id));
    }

    @PostMapping(value = {"/card"})
    public ResponseEntity<Card> createCard(@RequestBody CardRequest cardRequest) {
        return ResponseEntity.ok(cardService.saveCard(cardRequest));
    }

    @PutMapping(value = {"/card/{id}"})
    public ResponseEntity<Optional<Card>> updateCard(@PathVariable("id") int id, @RequestBody CardRequest cardRequest) {
        return ResponseEntity.ok(cardService.updateCard(id, cardRequest));
    }

    @GetMapping(value = {"/card/{id}"})
    public ResponseEntity<Card> getCard(@PathVariable("id") int id) {
        return cardService.getCard(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = {"/cards"})
    public ResponseEntity<Iterable<Card>> getCards() {
        return ResponseEntity.ok(cardService.getCards());
    }
}
