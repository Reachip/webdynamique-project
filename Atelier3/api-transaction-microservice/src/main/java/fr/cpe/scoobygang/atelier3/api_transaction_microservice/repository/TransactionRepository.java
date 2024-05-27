package fr.cpe.scoobygang.atelier3.api_transaction_microservice.repository;

import fr.cpe.scoobygang.common.model.Transaction;
import fr.cpe.scoobygang.common.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    List<Transaction> findByOwner(User user);
}
