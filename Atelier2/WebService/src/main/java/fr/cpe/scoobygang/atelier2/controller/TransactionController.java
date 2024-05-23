package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.model.Transaction;
import fr.cpe.scoobygang.atelier2.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(value = {"/transaction/{id}"})
    public ResponseEntity<List<Transaction>> getTransaction(@RequestHeader(value = "Authorization") String authorization, @PathVariable int id){
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }
}
