package fr.cpe.scoobygang.atelier3.api_transaction_microservice.repository;

import fr.cpe.scoobygang.common.security.model.Store;
import org.springframework.data.repository.CrudRepository;

public interface StoreRepository extends CrudRepository<Store, Integer> {

}
