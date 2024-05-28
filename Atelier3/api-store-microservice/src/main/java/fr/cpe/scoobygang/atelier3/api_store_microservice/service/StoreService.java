package fr.cpe.scoobygang.atelier3.api_store_microservice.service;

import fr.cpe.scoobygang.common.model.*;
import fr.cpe.scoobygang.common.repository.CardRepository;
import fr.cpe.scoobygang.common.repository.StoreRepository;
import fr.cpe.scoobygang.common.repository.UserRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class StoreService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final StoreRepository storeRepository;

    public StoreService(UserRepository userRepository, CardRepository cardRepository, StoreRepository storeRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.storeRepository = storeRepository;
    }

    public boolean sellUserCard(int cardId, int storeId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found for id " + cardId));

//        if (card.getStore() == null)
//        {
//            return false;
//        }

        // Mettre la carte en vente
        //card.setOnSale(true);
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found for id " + storeId));
        card.setStore(store);
        cardRepository.save(card);

        return true;
    }

    public boolean buyCard(String authorization, int cardId, int userId, int storeId){
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
        currentOwner.setBalance(currentOwner.getBalance() + card.getPrice());
        // On définit le nouveau propriétaire de la carte
        card.setOwner(newOwner);
        //card.setOnSale(false);
        card.setStore(null);

        // On lui débite le prix de la carte
        newOwner.setBalance(newOwner.getBalance() - card.getPrice());

        cardRepository.save(card);
        userRepository.save(newOwner);
        userRepository.save(currentOwner);

        saveTransaction(authorization, userId, cardId, storeId, TransactionAction.BUY);
        saveTransaction(authorization, currentOwner.getId(), cardId, storeId, TransactionAction.SELL);

        return true;
    }


    public void saveTransaction(String authorization, int userId, int cardId, int storeId, TransactionAction action)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        TransactionRequest transactionRequest = new TransactionRequest(userId, cardId, storeId, action);


        HttpEntity<Transaction> request = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Transaction> responseTransaction = restTemplate.exchange("http://localhost:8086/transaction/transaction/create", HttpMethod.POST, transactionRequest, Transaction.class);
    }

    public boolean cancelSellCard(int cardId, int storeId){
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found for id " + cardId));

        //if (card.getStore().getId() != storeId) return false;

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

    public List<Card> getCardsForUser(String authorization)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<User> request = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Card>> responseListCard = restTemplate.exchange("http://localhost:8086/card/cards/user", HttpMethod.GET, request, new ParameterizedTypeReference<List<Card>>() {});
        if (responseListCard.getStatusCode().is2xxSuccessful())
        {
            return responseListCard.getBody();
        }

        return null;
    }
}
