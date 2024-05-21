package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.service.CardService;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
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

    @PostConstruct
    public void init (){
        List<Card> cards = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath: /ressources/card.json" );
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

    @RequestMapping(value = {"/card/buy"}, method = RequestMethod.GET)
    public List<Card> buyCard(){
        return cardService.getAllCard();
    }

    @RequestMapping(value = {"/card/buy"}, method = RequestMethod.POST)
    public void buyCard(@RequestBody int cardId, int userId){
        cardService.buyCard(cardId,userId);
    }

    @RequestMapping(value = {"/card/sell"}, method = RequestMethod.GET)
    public void sellCard(@RequestBody int userId){
        cardService.getAllUserCard(userId);
    }

    @RequestMapping(value = {"/card/sell"}, method = RequestMethod.POST)
    public void sellCard(@RequestBody int cardId, int userId){
        cardService.sellUserCard(cardId, userId);
    }

}
