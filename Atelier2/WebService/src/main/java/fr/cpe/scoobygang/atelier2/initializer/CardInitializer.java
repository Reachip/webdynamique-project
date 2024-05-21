package fr.cpe.scoobygang.atelier2.initializer;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.resource.CardResource;
import fr.cpe.scoobygang.atelier2.service.CardService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardInitializer implements Initializer {
    private final CardResource cardResource;
    private final CardService cardService;
    public CardInitializer(CardResource cardResource, CardService cardService) {
        this.cardResource = cardResource;
        this.cardService = cardService;
    }

    @Override
    public void initialize() {
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
