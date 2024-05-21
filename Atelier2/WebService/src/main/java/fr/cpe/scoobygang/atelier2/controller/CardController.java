package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.resource.CardResource;
import fr.cpe.scoobygang.atelier2.service.CardService;
import fr.cpe.scoobygang.atelier2.service.StoreService;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CardController {
    private final CardService cardService;
    private final CardResource cardResource;
    private final StoreService storeService;
    public CardController(CardService cardService, CardResource cardResource, StoreService storeService) {
        this.cardService = cardService;
        this.cardResource = cardResource;
        this.storeService = storeService;
    }

    @PostConstruct
    public void init() {
        List<Card> cards = new ArrayList<>();
        try {
            File file = cardResource.load().getFile();
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Card card = Card.toCard(jsonObject);
                cards.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cardService.saveCards(cards);
    }

    @GetMapping(value = {"/card/buy"})
    public ResponseEntity<List<Card>> buyCard(){
        return ResponseEntity.ok(cardService.getAllCard());
    }

    @PostMapping(value = {"/card/buy"})
    public ResponseEntity buyCard(@RequestBody int cardId, int userId){
        storeService.buyCard(cardId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = {"/card/sell"})
    public ResponseEntity<List<Card>> sellCard(@RequestBody int userId){
        return ResponseEntity.ok(cardService.getAllUserCard(userId));
    }

    @PostMapping(value = {"/card/sell"})
    public void sellCard(@RequestBody int cardId, int userId){
        storeService.sellUserCard(cardId, userId);
    }
}
