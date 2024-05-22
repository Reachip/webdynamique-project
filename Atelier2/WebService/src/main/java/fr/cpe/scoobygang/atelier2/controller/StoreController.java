package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.initializer.CardInitializer;
import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.service.CardService;
import fr.cpe.scoobygang.atelier2.service.StoreService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class StoreController {
    private final CardService cardService;
    private final CardInitializer cardInitializer;
    private final StoreService storeService;

    public StoreController(CardService cardService, CardInitializer cardInitializer, StoreService storeService) {
        this.cardService = cardService;
        this.cardInitializer = cardInitializer;
        this.storeService = storeService;
    }

    @PostConstruct
    public void init() {
        cardInitializer.initialize();
    }

    @GetMapping(value = {"/store/buy"})
    public ResponseEntity<List<Card>> buyCard(){
        return ResponseEntity.ok(cardService.getAllCard());
    }

    @PostMapping(value = {"/store/buy"})
    public ResponseEntity buyCard(@RequestBody int cardId, int userId){
        storeService.buyCard(cardId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = {"/store/sell"})
    public ResponseEntity<List<Card>> sellCard(@RequestBody int userId){
        return ResponseEntity.ok(cardService.getAllUserCard(userId));
    }

    @PostMapping(value = {"/store/sell"})
    public void sellCard(@RequestBody int cardId, int userId){
        storeService.sellUserCard(cardId, userId);
    }
}
