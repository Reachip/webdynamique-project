package fr.cpe.scoobygang.common.repository;

import fr.cpe.scoobygang.common.model.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StoreRepository extends CrudRepository<Store, Integer> {
    @Query("SELECT count(c) FROM Store c")
    long count();

}
