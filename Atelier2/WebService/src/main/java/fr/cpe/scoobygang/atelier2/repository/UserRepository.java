package fr.cpe.scoobygang.atelier2.repository;

import fr.cpe.scoobygang.atelier2.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
