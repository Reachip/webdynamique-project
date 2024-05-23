package fr.cpe.scoobygang.atelier2.service;

import fr.cpe.scoobygang.atelier2.model.*;
import fr.cpe.scoobygang.atelier2.repository.CardRepository;
import fr.cpe.scoobygang.atelier2.repository.StoreRepository;
import fr.cpe.scoobygang.atelier2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class StoreService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final StoreRepository storeRepository;
    private final TransactionService transactionService;

    public StoreService(UserRepository userRepository, CardRepository cardRepository, StoreRepository storeRepository, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.storeRepository = storeRepository;
        this.transactionService = transactionService;
    }

    public boolean sellUserCard(int cardId, int storeId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found for id " + cardId));

        if (card.getStore() == null)
        {
            return false;
        }

        // Mettre la carte en vente
        //card.setOnSale(true);
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found for id " + storeId));
        card.setStore(store);
        cardRepository.save(card);

        return true;
    }

    public boolean buyCard(int cardId, int userId, int storeId){
        User newOwner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id " + userId));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found for id " + cardId));

        if (!newOwner.canBuy(card.getPrice()) || newOwner.getId() == card.getOwner().getId()){
            return false;
        }

        // Récupération du propriétaire actuel de la carte
        User currentOwner = card.getOwner();
        // On lui crédite l'argent de la vente
        currentOwner.setAccount(currentOwner.getAccount() + card.getPrice());
        // On définit le nouveau propriétaire de la carte
        card.setOwner(newOwner);
        //card.setOnSale(false);
        card.setStore(null);

        // On lui débite le prix de la carte
        newOwner.setAccount(newOwner.getAccount() - card.getPrice());

        cardRepository.save(card);
        userRepository.save(newOwner);
        userRepository.save(currentOwner);

        Transaction buyTransaction = transactionService.createTransaction(userId, cardId, storeId, TransactionAction.BUY);
        transactionService.createTransaction(currentOwner.getId(), cardId, storeId, TransactionAction.SELL);

        return true;
    }


    public boolean cancelSellCard(int cardId, int storeId){
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found for id " + cardId));

        if (card.getStore().getId() != storeId) return false;

        card.setStore(null);

        cardRepository.save(card);

        return true;
    }


    public void saveStores(List<Store> stores) {
        storeRepository.saveAll(stores);
    }

    public List<Card> getCardsById(int storeId) {
        return cardRepository.findByStoreId(storeId);
    }

    public List<Store> getStores() {
        Iterable<Store> iterable = storeRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }
}
