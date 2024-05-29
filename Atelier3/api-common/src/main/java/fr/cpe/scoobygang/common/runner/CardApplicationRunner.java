package fr.cpe.scoobygang.common.runner;

import fr.cpe.scoobygang.common.model.Card;
import fr.cpe.scoobygang.common.model.Store;
import fr.cpe.scoobygang.common.model.User;
import fr.cpe.scoobygang.common.repository.CardRepository;
import fr.cpe.scoobygang.common.repository.StoreRepository;
import fr.cpe.scoobygang.common.repository.UserRepository;
import fr.cpe.scoobygang.common.resource.CardResource;

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
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final CardRepository cardRepository;

    public CardApplicationRunner(CardResource cardResource, UserRepository userRepository, StoreRepository storeRepository, CardRepository cardRepository) {
        this.cardResource = cardResource;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (cardRepository.count() > 0) {
            return;
        }

        List<Card> cards = new ArrayList<>();
        Random random = new Random();

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

        cardRepository.saveAll(cards);
    }
}
