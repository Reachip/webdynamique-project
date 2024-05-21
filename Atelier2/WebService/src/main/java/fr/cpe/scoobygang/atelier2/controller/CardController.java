package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.service.CardService;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CardController {
    @Autowired
    private CardService cardService;

    public Resource loadCards() {
        return new ClassPathResource("card.json");
    }

    @PostConstruct
    public void init() {
        List<Card> cards = new ArrayList<>();
        try {
            File file = loadCards().getFile();
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
    @RequestMapping(value = {"/card/buy"}, method = RequestMethod.GET)
    public ResponseEntity<List<Card>> buyCard(){
        return ResponseEntity.ok(cardService.getAllCard());
    }

    @RequestMapping(value = {"/card/buy"}, method = RequestMethod.POST)
    public ResponseEntity buyCard(@RequestBody int cardId, int userId){
        if (cardService.buyCard(cardId,userId)) return ResponseEntity.ok(null);
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = {"/card/sell"}, method = RequestMethod.GET)
    public ResponseEntity<List<Card>> sellCard(@RequestBody int userId){
        return ResponseEntity.ok(cardService.getAllUserCard(userId));
    }

    @RequestMapping(value = {"/card/sell"}, method = RequestMethod.POST)
    public void sellCard(@RequestBody int cardId, int userId){
        cardService.sellUserCard(cardId, userId);
    }

}
