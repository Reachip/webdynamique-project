package fr.cpe.scoobygang.atelier2.service;

import fr.cpe.scoobygang.atelier2.model.*;
import fr.cpe.scoobygang.atelier2.repository.CardRepository;
import fr.cpe.scoobygang.atelier2.repository.StoreRepository;
import fr.cpe.scoobygang.atelier2.repository.TransactionRepository;
import fr.cpe.scoobygang.atelier2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StoreService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    private final StoreRepository storeRepository;
    public StoreService(UserRepository userRepository, CardRepository cardRepository, TransactionRepository transactionRepository, StoreRepository storeRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
        this.storeRepository = storeRepository;
    }

    public void sellUserCard(int cardId, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id " + userId));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found for id " + cardId));

        if (!user.getCardList().contains(card)) {
            throw new IllegalStateException("Card not owned by the user.");
        }

        // Retirer la carte de la liste de l'utilisateur
        user.getCardList().remove(card);
        card.setOwner(null); // Mise à jour de la relation à null

        // Sauvegarder les modifications
        userRepository.save(user);
        cardRepository.save(card);
    }

    public void buyCard(int cardId, int userId, int storeId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id " + userId));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found for id " + cardId));

        card.setOwner(user);
        user.getCardList().add(card);

        cardRepository.save(card);
        userRepository.save(user);

        createTransaction(userId, cardId, storeId, TransactionAction.BUY);
        createTransaction(card.getLastUserId(), cardId, storeId, TransactionAction.SELL);
    }

    public void createTransaction(int userId, int cardId, int storeId, TransactionAction action) {
        User owner = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found"));

        // Create new Transaction
        Transaction transaction = new Transaction();
        transaction.setOwner(owner);
        transaction.setCard(card);
        transaction.setStore(store);
        transaction.setAction(action);
        transaction.setTimestamp(new Timestamp(System.currentTimeMillis()));

        // Save the new Transaction
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransaction(int userId){
        //Get user from userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id " + userId));
        Iterable<Transaction> iterable = transactionRepository.findByOwner(user);
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public void saveStores(List<Store> stores) {
        storeRepository.saveAll(stores);
    }
}
