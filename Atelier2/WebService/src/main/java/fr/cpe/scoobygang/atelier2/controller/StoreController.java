package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.initializer.CardInitializer;
import fr.cpe.scoobygang.atelier2.initializer.StoreInitializer;
import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.Transaction;
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
    private final StoreInitializer storeInitializer;

    public StoreController(CardService cardService, CardInitializer cardInitializer, StoreService storeService, StoreInitializer storeInitializer) {
        this.cardService = cardService;
        this.cardInitializer = cardInitializer;
        this.storeService = storeService;
        this.storeInitializer = storeInitializer;
    }

    @PostConstruct
    public void init() {
        cardInitializer.initialize();
        storeInitializer.initialize();
    }

    @GetMapping(value = {"/store/buy"})
    public ResponseEntity<List<Card>> buyCard(){
        return ResponseEntity.ok(cardService.getAllCard());
    }

    @PostMapping(value = {"/store/buy"})
    public ResponseEntity buyCard(@RequestBody int cardId, int userId, int storeId){
        storeService.buyCard(cardId, userId, storeId);
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

    @GetMapping(value = {"/store/transaction"})
    public ResponseEntity<List<Transaction>> getTransaction(@RequestBody int userId){
        return ResponseEntity.ok(storeService.getTransaction(userId));
    }
}
