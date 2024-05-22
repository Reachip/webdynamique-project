package fr.cpe.scoobygang.atelier2.service;

import fr.cpe.scoobygang.atelier2.model.*;
import fr.cpe.scoobygang.atelier2.repository.CardRepository;
import fr.cpe.scoobygang.atelier2.repository.StoreRepository;
import fr.cpe.scoobygang.atelier2.repository.TransactionRepository;
import fr.cpe.scoobygang.atelier2.repository.UserRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TransactionService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final StoreRepository storeRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(UserRepository userRepository, CardRepository cardRepository, StoreRepository storeRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.storeRepository = storeRepository;
        this.transactionRepository = transactionRepository;
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
}
