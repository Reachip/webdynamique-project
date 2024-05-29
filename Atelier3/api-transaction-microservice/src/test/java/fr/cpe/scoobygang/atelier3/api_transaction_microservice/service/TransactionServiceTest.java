package fr.cpe.scoobygang.atelier3.api_transaction_microservice.service;

import fr.cpe.scoobygang.common.dto.request.UserRequest;
import fr.cpe.scoobygang.common.model.*;
import fr.cpe.scoobygang.common.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TransactionService transactionService;

    private String token = "test-token";
    private int userId = 1;
    private int cardId = 1;
    private int storeId = 1;
    private TransactionAction action = TransactionAction.SELL;

    private UserRequest userRequest;
    private Card card;
    private Store store;
    private User user;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        userRequest = new UserRequest();
        userRequest.setId(userId);
        userRequest.setName("John Doe");

        card = new Card();
        card.setId(cardId);
        card.setPrice(100.0);

        store = new Store();
        store.setId(storeId);
        store.setName("Test Store");

        user = new User();
        user.setId(userId);
        user.setName("John Doe");

        transaction = new Transaction();
        transaction.setOwner(user);
        transaction.setCard(card);
        transaction.setStore(store);
        transaction.setAction(action);
        transaction.setAmount(card.getPrice());
        transaction.setTimestamp(new Timestamp(System.currentTimeMillis()));
    }

    // @Test
    void testCreateTransaction_Success() {
        when(restTemplate.getForEntity(eq("http://localhost:8085/user/" + userId), eq(UserRequest.class)))
                .thenReturn(new ResponseEntity<>(userRequest, HttpStatus.OK));
        when(restTemplate.getForEntity(eq("http://localhost:8086/card/" + cardId), eq(Card.class)))
                .thenReturn(new ResponseEntity<>(card, HttpStatus.OK));
        when(restTemplate.getForEntity(eq("http://localhost:8086/store/" + storeId), eq(Store.class)))
                .thenReturn(new ResponseEntity<>(store, HttpStatus.OK));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        boolean result = transactionService.createTransaction(token, userId, cardId, storeId, action);
        assertTrue(result);
    }

    // @Test
    void testCreateTransaction_UserRequestFails() {
        when(restTemplate.getForEntity(eq("http://localhost:8085/user/user/" + userId), eq(UserRequest.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        boolean result = transactionService.createTransaction(token, userId, cardId, storeId, action);
        assertFalse(result);
    }

    // @Test
    void testGetTransaction_Success() {
        when(restTemplate.getForEntity(eq("http://localhost:8085/user/user/" + userId), eq(UserRequest.class)))
                .thenReturn(new ResponseEntity<>(userRequest, HttpStatus.OK));
        when(transactionRepository.findByOwner(any(User.class)))
                .thenReturn(Arrays.asList(transaction));

        List<Transaction> transactions = transactionService.getTransaction(token, userId);
        assertEquals(1, transactions.size());
        assertEquals(transaction, transactions.get(0));
    }

    // @Test
    void testGetTransaction_UserRequestFails() {
        when(restTemplate.getForEntity(eq("http://localhost:8085/user/user/" + userId), eq(UserRequest.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        List<Transaction> transactions = transactionService.getTransaction(token, userId);
        assertNull(transactions);
    }
}