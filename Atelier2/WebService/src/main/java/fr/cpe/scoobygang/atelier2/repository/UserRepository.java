package fr.cpe.scoobygang.atelier2.repository;

import fr.cpe.scoobygang.atelier2.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
