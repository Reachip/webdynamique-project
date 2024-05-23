package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.model.Transaction;
import fr.cpe.scoobygang.atelier2.service.TransactionService;
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
