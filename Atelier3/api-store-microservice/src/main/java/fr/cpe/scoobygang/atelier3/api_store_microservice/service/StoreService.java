package fr.cpe.scoobygang.atelier3.api_store_microservice.service;

import fr.cpe.scoobygang.common.dto.mapper.CardMapper;
import fr.cpe.scoobygang.common.dto.mapper.UserMapper;
import fr.cpe.scoobygang.common.dto.request.CardRequest;
import fr.cpe.scoobygang.common.dto.request.TransactionRequest;
import fr.cpe.scoobygang.common.dto.request.UserRequest;
import fr.cpe.scoobygang.common.model.*;
import fr.cpe.scoobygang.common.repository.StoreRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public boolean sellUserCard(String authorization, int cardId, int storeId) {
        Card card = getCard(authorization, cardId);
        // Mettre la carte en vente
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found for id " + storeId));
        card.setStore(store);
        saveCard(authorization, card);

        return true;
    }

    public boolean buyCard(String authorization, int cardId, int userId, int storeId){

        // Récupération du nouvel acheteur
        User newOwner = getUser(authorization, userId);
        if (newOwner == null) return false;

        // Récupération de la carte
        Card card = getCard(authorization, cardId);
        if (card == null) return false;

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

        saveCard(authorization, card);
        saveUser(authorization, newOwner);
        saveUser(authorization, currentOwner);

        saveTransaction(authorization, userId, cardId, storeId, TransactionAction.BUY);
        saveTransaction(authorization, currentOwner.getId(), cardId, storeId, TransactionAction.SELL);

        return true;
    }


    public void saveTransaction(String authorization, int userId, int cardId, int storeId, TransactionAction action)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setUserId(userId);
        transactionRequest.setCardId(cardId);
        transactionRequest.setStoreId(storeId);
        transactionRequest.setAction(action);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:8086/transaction/create", transactionRequest, Void.class);
    }

    public void saveCard(String authorization, Card card) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<Card> request = new HttpEntity<>(card, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:8086/card/save", request, Void.class);
    }

    public void saveUser(String authorization, User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:8086/user/save", request, Void.class);
    }

    public Card getCard(String authorization,int cardId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<Card> request = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CardRequest> cardRequest =  restTemplate.getForEntity("http://localhost:8086/card/"+cardId, null, Card.class);
        if (cardRequest.getStatusCode().is2xxSuccessful())
        {
            return CardMapper.INSTANCE.cardRequestToCard(cardRequest.getBody());
        }
        return null;
    }

    public List<Card> getCardsFromStore(int storeId) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Card> request = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Card>> responseListCard = restTemplate.exchange("http://localhost:8086/card/store/"+storeId, HttpMethod.GET, request, new ParameterizedTypeReference<List<Card>>() {});
        if (responseListCard.getStatusCode().is2xxSuccessful())
        {
            return responseListCard.getBody();
        }

        return null;
    }

    public User getUser(String authorization,int userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<User> request = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserRequest> userRequest =  restTemplate.getForEntity("http://localhost:8085/user/user/"+userId, null, UserRequest.class);
        if (userRequest.getStatusCode().is2xxSuccessful())
        {
            return UserMapper.INSTANCE.userRequestToUser(userRequest.getBody());
        }
        return null;
    }

    public boolean cancelSellCard(String authorization,int cardId, int storeId){
        Card card = getCard(authorization, cardId);
        if (card == null) return false;

        card.setStore(null);
        saveCard(authorization, card);

        return true;
    }

    public void saveStores(List<Store> stores) {
        storeRepository.saveAll(stores);
    }

    public List<Card> getCardsById(int storeId) {
        return getCardsFromStore(storeId);
    }

    public List<Store> getStores() {
        Iterable<Store> iterable = storeRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

    public Store getStore(int storeID){
        return storeRepository.findById(storeID).get();
    }

    public List<Card> getCardsForUser(String authorization)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        HttpEntity<User> request = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Card>> responseListCard = restTemplate.exchange("http://localhost:8086/card/user", HttpMethod.GET, request, new ParameterizedTypeReference<List<Card>>() {});
        if (responseListCard.getStatusCode().is2xxSuccessful())
        {
            return responseListCard.getBody();
        }

        return null;
    }
}
