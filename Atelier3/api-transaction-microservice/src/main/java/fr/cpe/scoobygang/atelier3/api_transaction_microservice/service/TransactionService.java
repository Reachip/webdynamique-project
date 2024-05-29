package fr.cpe.scoobygang.atelier3.api_transaction_microservice.service;

import fr.cpe.scoobygang.common.dto.mapper.CardMapper;
import fr.cpe.scoobygang.common.dto.mapper.StoreMapper;
import fr.cpe.scoobygang.common.dto.mapper.UserMapper;
import fr.cpe.scoobygang.common.dto.request.UserRequest;
import fr.cpe.scoobygang.common.dto.response.CardResponse;
import fr.cpe.scoobygang.common.dto.response.StoreResponse;
import fr.cpe.scoobygang.common.model.*;
import fr.cpe.scoobygang.common.repository.TransactionRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
    private final RestTemplate restTemplate;

    public TransactionService(TransactionRepository transactionRepository, RestTemplate restTemplate) {
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    public boolean createTransaction(String token, int userId, int cardId, int storeId, TransactionAction action) {
        System.out.println("token!!!!! :"+token);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Récupération de l'utilisateur
        ResponseEntity<UserRequest> userRequest = restTemplate.exchange("http://localhost:8080/user/"+userId, HttpMethod.GET,entity,UserRequest.class);

        if (!userRequest.getStatusCode().is2xxSuccessful()) return false;

        System.out.println("owner !!!: "+userRequest.getBody());
        User owner = UserMapper.INSTANCE.userRequestToUser(userRequest.getBody());

        ResponseEntity<CardResponse> cardResponse =  restTemplate.exchange("http://localhost:8080/card/"+cardId, HttpMethod.GET,entity,CardResponse.class);

        if (!cardResponse.getStatusCode().is2xxSuccessful()) return false;
        Card card = CardMapper.INSTANCE.cardReponseToCard(cardResponse.getBody());

        ResponseEntity<StoreResponse> storeResponse =  restTemplate.exchange("http://localhost:8080/store/"+storeId, HttpMethod.GET ,entity, StoreResponse.class);
        if (!storeResponse.getStatusCode().is2xxSuccessful()) return false;

        Store store = StoreMapper.INSTANCE.StoreResponsesToStore(storeResponse.getBody());

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

        HttpEntity<User> request = new HttpEntity<>(headers);

        //Get user from userId
        ResponseEntity<UserRequest> userRequest = restTemplate.exchange("http://localhost:8085/user/user/"+userId, HttpMethod.GET, request, UserRequest.class);

        if (!userRequest.getStatusCode().is2xxSuccessful()) return null;
        User user = UserMapper.INSTANCE.userRequestToUser(userRequest.getBody());

        Iterable<Transaction> iterable = transactionRepository.findByOwner(user);
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
