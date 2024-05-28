package fr.cpe.scoobygang.atelier3.api_transaction_microservice.controller;

import fr.cpe.scoobygang.atelier3.api_transaction_microservice.service.TransactionService;
import fr.cpe.scoobygang.common.dto.request.TransactionRequest;
import fr.cpe.scoobygang.common.jwt.JWT;
import fr.cpe.scoobygang.common.jwt.JWTService;
import fr.cpe.scoobygang.common.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final JWTService jwtService;

    public TransactionController(TransactionService transactionService, JWTService jwtService) {
        this.transactionService = transactionService;
        this.jwtService = jwtService;
    }

    @GetMapping(value = {"/{userId}"})
    public ResponseEntity<List<Transaction>> getTransaction(@RequestHeader(value = "Authorization") String authorization, @PathVariable int userId){
        return ResponseEntity.ok(transactionService.getTransaction(authorization,userId));
    }

    @PostMapping(value = {"/create"})
    public ResponseEntity createTransaction(@RequestHeader(value = "Authorization") String authorization, @RequestBody TransactionRequest transactionRequest){
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);
        if (jwt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (transactionService.createTransaction(authorization, transactionRequest.getUserId(),
                transactionRequest.getCardId(), transactionRequest.getStoreId(), transactionRequest.getAction())) return ResponseEntity.ok(null);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
}
