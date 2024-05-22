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

        // Mettre la carte en vente
        card.setOnSale(true);

        // Sauvegarder les modifications
        userRepository.save(user);
        cardRepository.save(card);
    }

    public boolean buyCard(int cardId, int userId, int storeId){
        User newOwner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id " + userId));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found for id " + cardId));

        if (!checkUserAccount(newOwner, card.getPrice())){
            return false;
        }

        // Récupération du propriétaire actuel de la carte
        User currentOwner = card.getOwner();
        // On supprime la carte de la liste du propriétaire actuel
        currentOwner.getCardList().remove(card);
        // On lui crédite l'argent de la vente
        currentOwner.setAccount(currentOwner.getAccount() + card.getPrice());
        // On définit le nouveau propriétaire de la carte
        card.setOwner(newOwner);
        // On lui ajoute la carte à sa liste car elle lui appartient désormais
        newOwner.getCardList().add(card);
        // On lui débite le prix de la carte
        newOwner.setAccount(newOwner.getAccount() - card.getPrice());

        cardRepository.save(card);
        userRepository.save(newOwner);
        userRepository.save(currentOwner);

        createTransaction(userId, cardId, storeId, TransactionAction.BUY);
        createTransaction(currentOwner.getId(), cardId, storeId, TransactionAction.SELL);

        return true;
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
        transaction.setAmount(card.getPrice());
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

    public boolean checkUserAccount(User user, double price){
        return user.getAccount() >= price;
    }

    public List<Card> getCardsById(int storeId) {
        return  null;
    }
}
