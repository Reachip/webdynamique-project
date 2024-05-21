package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.resource.CardResource;
import fr.cpe.scoobygang.atelier2.service.CardService;
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
public class CardController {
    @Autowired
    private CardService cardService;

    @Autowired
    private CardResource cardResource;

    @PostConstruct
    public void init() {
        List<Card> cards = new ArrayList<>();
        try {
            File file = cardResource.load().getFile();
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Card card = jsonObjectToCard(jsonObject);
                cards.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cardService.saveCards(cards);
    }

    private Card jsonObjectToCard(JSONObject jsonObject) {
        Card card = new Card();
        card.setName(jsonObject.getString("name"));
        card.setDescription(jsonObject.getString("description"));
        card.setFamily(jsonObject.getString("family"));
        card.setAffinity(jsonObject.getString("affinity"));
        card.setImgUrl(jsonObject.getString("imgUrl"));
        card.setSmallImgUrl(jsonObject.getString("smallImgUrl"));
        card.setEnergy(jsonObject.getInt("energy"));
        card.setHp(jsonObject.getDouble("hp"));
        card.setDefence(jsonObject.getDouble("defence"));
        card.setAttack(jsonObject.getDouble("attack"));
        card.setPrice(jsonObject.getDouble("price"));
        return card;
    }

    // Afficher toutes cartes disponibles à l'achat
    @GetMapping(value = {"/card/buy"})
    public ResponseEntity<List<Card>> buyCard(){
        return ResponseEntity.ok(cardService.getAllCard());
    }

    @PostMapping(value = {"/card/buy"})
    public ResponseEntity buyCard(@RequestBody int cardId, int userId){
        if (cardService.buyCard(cardId,userId)) return ResponseEntity.ok(null);
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @GetMapping(value = {"/card/sell"})
    public ResponseEntity<List<Card>> sellCard(@RequestBody int userId){
        return ResponseEntity.ok(cardService.getAllUserCard(userId));
    }

    @PostMapping(value = {"/card/sell"})
    public void sellCard(@RequestBody int cardId, int userId){
        cardService.sellUserCard(cardId, userId);
    }

}
