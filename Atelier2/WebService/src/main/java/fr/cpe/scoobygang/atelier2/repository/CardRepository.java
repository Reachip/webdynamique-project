package fr.cpe.scoobygang.atelier2.repository;

import fr.cpe.scoobygang.atelier2.model.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Integer> {
    List<Card> findByOwnerId(int userId);

    @Query("SELECT c FROM Card c WHERE c.isOnSale = TRUE")
    List<Card> findOnSaleCards();

    @Query("SELECT c FROM Card c WHERE c.owner IS NULL")
    List<Card> findNotOwnerCards();

    List<Card> findByStoreId(int storeId);
}
