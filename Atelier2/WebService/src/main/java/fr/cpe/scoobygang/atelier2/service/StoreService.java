package fr.cpe.scoobygang.atelier2.service;

import fr.cpe.scoobygang.atelier2.model.*;
import fr.cpe.scoobygang.atelier2.repository.CardRepository;
import fr.cpe.scoobygang.atelier2.repository.StoreRepository;
import fr.cpe.scoobygang.atelier2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


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

        if (!newOwner.canBuy(card.getPrice())){
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

        transactionService.createTransaction(userId, cardId, storeId, TransactionAction.BUY);
        transactionService.createTransaction(currentOwner.getId(), cardId, storeId, TransactionAction.SELL);

        return true;
    }


    public void saveStores(List<Store> stores) {
        storeRepository.saveAll(stores);
    }


}
