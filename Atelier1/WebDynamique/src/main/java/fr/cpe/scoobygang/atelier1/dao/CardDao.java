package fr.cpe.scoobygang.atelier1.dao;

import fr.cpe.scoobygang.atelier1.model.Card;
import fr.cpe.scoobygang.atelier1.service.RequestService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class CardDao {
    @Autowired
    private RequestService requestService;

    @Value("${url.card}")
    private String cardURL;

    private final List<Card> cardList = new ArrayList<>();
    private final Random randomGenerator = new Random();
    private final URL url = new URL(cardURL);

    public CardDao() throws MalformedURLException {
        createCardList();
    }

    private void createCardList() {
        try {
            JSONArray jsonArray = requestService.getObjects(url);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Card card = Card.fromJsonObject(jsonObject);
                cardList.add(card);
            }
        } catch (Exception why) {
            why.printStackTrace();
        }
    }

    public Card getRandomCard() {
        int index = randomGenerator.nextInt(cardList.size());
        return cardList.get(index);
    }
}
