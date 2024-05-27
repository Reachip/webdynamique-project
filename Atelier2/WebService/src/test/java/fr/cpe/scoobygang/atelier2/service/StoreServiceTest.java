package fr.cpe.scoobygang.atelier2.service;

import com.github.javafaker.Faker;
import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.Store;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.repository.CardRepository;
import fr.cpe.scoobygang.atelier2.repository.StoreRepository;
import fr.cpe.scoobygang.atelier2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreServiceTest {
    @Autowired
    private StoreService storeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void sellUserCard() {
        Card card = new Card();
        card.setName("Card 1");

        cardRepository.save(card);

        User user = User.toUser(new Faker());
        userRepository.save(user);

        Store store = new Store();
        store.setName("Store 1");

        storeRepository.save(store);

        boolean isSelling = storeService.sellUserCard(card.getId(), store.getId());
        assertTrue(isSelling);


        Card cardUpdated = cardRepository.findById(card.getId()).get();
        assertEquals(store.getId(), cardUpdated.getStore().getId());
    }

    @Test
    void buyCard() {
    }

    @Test
    void cancelSellCard() {
    }

    @Test
    void saveStores() {
    }

    @Test
    void getCardsById() {
    }

    @Test
    void getStores() {
    }
}