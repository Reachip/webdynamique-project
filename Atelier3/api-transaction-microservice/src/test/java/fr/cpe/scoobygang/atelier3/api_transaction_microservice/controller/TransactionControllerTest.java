package fr.cpe.scoobygang.atelier3.api_transaction_microservice.controller;

import fr.cpe.scoobygang.atelier3.api_transaction_microservice.service.TransactionService;
import fr.cpe.scoobygang.common.dto.request.TransactionRequest;
import fr.cpe.scoobygang.common.jwt.JWT;
import fr.cpe.scoobygang.common.jwt.JWTService;
import fr.cpe.scoobygang.common.model.Transaction;
import fr.cpe.scoobygang.common.model.TransactionAction;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    private final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private final String authorization = "Bearer " + token;
    @Mock
    private TransactionService transactionService;
    @Mock
    private JWTService jwtService;
    @InjectMocks
    private TransactionController transactionController;

    @Test
    void getTransaction() {
        int userId = 1;

        Mockito.when(transactionService.getTransaction(authorization, userId)).thenReturn(List.of(new Transaction(), new Transaction()));

        ResponseEntity<List<Transaction>> transaction = transactionController.getTransaction(
                authorization, userId
        );

        assertEquals(200, transaction.getStatusCode().value());
        assertEquals(2, transaction.getBody().size());
    }

    @Test
    void createTransactionWhenUnAuthorized() {
        Mockito.when(jwtService.fromAuthorization(authorization)).thenReturn(Optional.empty());

        TransactionRequest transactionRequest = new TransactionRequest();

        transactionRequest.setAction(TransactionAction.BUY);
        transactionRequest.setCardId(1);
        transactionRequest.setUserId(3);
        transactionRequest.setStoreId(2);

        ResponseEntity<List<Transaction>> transaction = transactionController.createTransaction(
                authorization, transactionRequest
        );

        assertEquals(401, transaction.getStatusCode().value());
    }

    @Test
    void createTransactionWhenAuthorizedAndCanCreateTransaction() throws JSONException {
        TransactionRequest transactionRequest = new TransactionRequest();

        transactionRequest.setAction(TransactionAction.BUY);
        transactionRequest.setCardId(1);
        transactionRequest.setUserId(3);
        transactionRequest.setStoreId(2);

        Mockito.when(jwtService.fromAuthorization(authorization)).thenReturn(Optional.of(new JWT(this.token)));
        Mockito.when(transactionService.createTransaction(
                authorization,
                transactionRequest.getUserId(),
                transactionRequest.getCardId(),
                transactionRequest.getStoreId(),
                transactionRequest.getAction())
        ).thenReturn(true);

        ResponseEntity<List<Transaction>> transaction = transactionController.createTransaction(
                authorization, transactionRequest
        );

        assertEquals(200, transaction.getStatusCode().value());
    }

    @Test
    void createTransactionWhenAuthorizedAndCantCreateTransaction() throws JSONException {
        TransactionRequest transactionRequest = new TransactionRequest();

        transactionRequest.setAction(TransactionAction.BUY);
        transactionRequest.setCardId(1);
        transactionRequest.setUserId(3);
        transactionRequest.setStoreId(2);

        Mockito.when(jwtService.fromAuthorization(authorization)).thenReturn(Optional.of(new JWT(this.token)));
        Mockito.when(transactionService.createTransaction(
                authorization,
                transactionRequest.getUserId(),
                transactionRequest.getCardId(),
                transactionRequest.getStoreId(),
                transactionRequest.getAction())
        ).thenReturn(false);

        ResponseEntity<List<Transaction>> transaction = transactionController.createTransaction(
                authorization, transactionRequest
        );

        assertEquals(400, transaction.getStatusCode().value());
    }
}