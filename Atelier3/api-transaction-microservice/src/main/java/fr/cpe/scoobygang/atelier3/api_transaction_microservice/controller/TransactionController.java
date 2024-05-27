package fr.cpe.scoobygang.atelier3.api_transaction_microservice.controller;

import fr.cpe.scoobygang.atelier3.api_transaction_microservice.service.TransactionService;
import fr.cpe.scoobygang.common.security.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(value = {"/{userId}"})
    public ResponseEntity<List<Transaction>> getTransaction(@RequestHeader(value = "Authorization") String authorization, @PathVariable int userId){
        return ResponseEntity.ok(transactionService.getTransaction(userId));
    }
}
