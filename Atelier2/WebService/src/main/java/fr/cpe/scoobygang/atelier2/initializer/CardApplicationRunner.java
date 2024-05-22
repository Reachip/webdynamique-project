package fr.cpe.scoobygang.atelier2.initializer;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.resource.CardResource;
import fr.cpe.scoobygang.atelier2.service.CardService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class CardApplicationRunner implements ApplicationRunner {
    private final CardResource cardResource;
    private final CardService cardService;

    public CardApplicationRunner(CardResource cardResource, CardService cardService) {
        this.cardResource = cardResource;
        this.cardService = cardService;
    }

    @Override
    public void run(ApplicationArguments args) {
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
}
