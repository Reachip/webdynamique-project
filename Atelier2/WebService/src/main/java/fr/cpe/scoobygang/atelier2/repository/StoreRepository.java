package fr.cpe.scoobygang.atelier2.repository;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoreRepository extends CrudRepository<Store, Integer> {
    @Query("select cardList from Store where id = ?1")
    List<Card> findCardsById(int storeId);
}
