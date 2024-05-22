package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.initializer.StoreApplicationRunner;
import fr.cpe.scoobygang.atelier2.mapper.CardMapper;
import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.Store;
import fr.cpe.scoobygang.atelier2.model.StoreOrder;
import fr.cpe.scoobygang.atelier2.model.Transaction;
import fr.cpe.scoobygang.atelier2.request.CardResponse;
import fr.cpe.scoobygang.atelier2.request.StoreOrderRequest;
import fr.cpe.scoobygang.atelier2.service.CardService;
import fr.cpe.scoobygang.atelier2.service.StoreService;
import fr.cpe.scoobygang.atelier2.service.TransactionService;
import jakarta.annotation.PostConstruct;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class StoreController {
    private final CardService cardService;
    private final StoreService storeService;
    private final TransactionService transactionService;

    private final StoreApplicationRunner storeApplicationRunner;

    public StoreController(CardService cardService, StoreService storeService, StoreApplicationRunner storeApplicationRunner, TransactionService transactionService) {
        this.cardService = cardService;
        this.storeService = storeService;
        this.storeApplicationRunner = storeApplicationRunner;
        this.transactionService = transactionService;
    }

    @GetMapping(value = {"/stores"})
    public ResponseEntity<List<Store>> getStores() {
        return ResponseEntity.ok(storeService.getStores());
    }

    @GetMapping(value = {"/store/buy"})
    public ResponseEntity<List<Card>> buyCard(){
        return ResponseEntity.ok(cardService.getAllCard());
    }

    @PostMapping(value = {"/store/buy"})
    public ResponseEntity buyCard(@RequestBody int cardId, int userId, int storeId){
        if (storeService.buyCard(cardId, userId, storeId)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping(value = {"/store/cards_to_sell/{storeId}"})
    public ResponseEntity<List<CardResponse>> sellCard(@PathVariable int storeId){
        return ResponseEntity.ok(CardMapper.INSTANCE.cardsToCardResponses(storeService.getCardsById(storeId)));
    }

    @PostMapping(value = {"/store/sell"})
    public ResponseEntity sellCard(@RequestBody StoreOrderRequest storeOrderRequest){
        if (storeService.sellUserCard(storeOrderRequest.getCardId(), storeOrderRequest.getStoreId())) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping(value = {"/store/transaction"})
    public ResponseEntity<List<Transaction>> getTransaction(@RequestBody int userId){
        return ResponseEntity.ok(transactionService.getTransaction(userId));
    }
}
