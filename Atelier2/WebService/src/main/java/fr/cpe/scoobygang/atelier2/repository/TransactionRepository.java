package fr.cpe.scoobygang.atelier2.repository;

import fr.cpe.scoobygang.atelier2.model.Transaction;
import fr.cpe.scoobygang.atelier2.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    List<Transaction> findByOwner(User user);
}
