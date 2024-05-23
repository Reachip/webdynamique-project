package fr.cpe.scoobygang.atelier2.runner;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.Store;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.repository.StoreRepository;
import fr.cpe.scoobygang.atelier2.repository.UserRepository;
import fr.cpe.scoobygang.atelier2.resource.CardResource;
import fr.cpe.scoobygang.atelier2.service.CardService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@Order(3)
public class CardApplicationRunner implements ApplicationRunner {
    private final CardResource cardResource;
    private final CardService cardService;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public CardApplicationRunner(CardResource cardResource, CardService cardService, UserRepository userRepository, StoreRepository storeRepository) {
        this.cardResource = cardResource;
        this.cardService = cardService;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<Card> cards = new ArrayList<>();

        try {
            File file = cardResource.load().getFile();
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                List<User> users = new ArrayList<>();
                userRepository.findAll().forEach(users::add);

                List<Store> stores = new ArrayList<>();
                storeRepository.findAll().forEach(stores::add);

                Collections.shuffle(users);
                Collections.shuffle(stores);

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Card card = Card.toCard(jsonObject);

                Random random = new Random();
                boolean shouldAddStore = random.nextBoolean();

                if (shouldAddStore) {
                    card.setStore(stores.stream().findFirst().orElse(null));
                } else {
                    card.setStore(null);
                }

                card.setOwner(users.stream().findFirst().orElse(null));

                cards.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cardService.saveCards(cards);
    }
}
