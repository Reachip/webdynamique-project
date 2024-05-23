package fr.cpe.scoobygang.atelier2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StoreOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "cardId", referencedColumnName = "id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "storeId", referencedColumnName = "id")
    private Store store;
}
