package fr.cpe.scoobygang.atelier3.api_transaction_microservice.service;

import fr.cpe.scoobygang.common.dto.mapper.CardMapper;
import fr.cpe.scoobygang.common.dto.mapper.UserMapper;
import fr.cpe.scoobygang.common.dto.request.UserRequest;
import fr.cpe.scoobygang.common.model.*;
import fr.cpe.scoobygang.common.repository.CardRepository;
import fr.cpe.scoobygang.common.repository.StoreRepository;
import fr.cpe.scoobygang.common.repository.TransactionRepository;
import fr.cpe.scoobygang.common.repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public boolean createTransaction(String token, int userId, int cardId, int storeId, TransactionAction action) {

        String authorization = "Bearer " + token;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);

        RestTemplate restTemplate = new RestTemplate();

        // Récupération de l'utilisateur
        ResponseEntity<UserRequest> userRequest = restTemplate.getForEntity("http://localhost:8085/user/user/"+userId, null, UserRequest.class);

        if (!userRequest.getStatusCode().is2xxSuccessful()) return false;
        User owner = UserMapper.INSTANCE.userRequestToUser(userRequest.getBody());

        ResponseEntity<Card> cardResponse =  restTemplate.getForEntity("http://localhost:8086/card/card/"+cardId, null, Card.class);
        if (!cardResponse.getStatusCode().is2xxSuccessful()) return false;
        Card card = cardResponse.getBody();

        ResponseEntity<Store> storeResponse =  restTemplate.getForEntity("http://localhost:8086/store/store/"+storeId, null, Store.class);
        if (!storeResponse.getStatusCode().is2xxSuccessful()) return false;
        Store store = storeResponse.getBody();

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

        return true;
    }

    public List<Transaction> getTransaction(String token, int userId){
        String authorization = "Bearer " + token;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);

        RestTemplate restTemplate = new RestTemplate();

        //Get user from userId
        ResponseEntity<UserRequest> userRequest = restTemplate.getForEntity("http://localhost:8085/user/user/"+userId, null, UserRequest.class);

        if (!userRequest.getStatusCode().is2xxSuccessful()) return null;
        User user = UserMapper.INSTANCE.userRequestToUser(userRequest.getBody());

        Iterable<Transaction> iterable = transactionRepository.findByOwner(user);
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
