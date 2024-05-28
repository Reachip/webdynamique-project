package fr.cpe.scoobygang.common.model;

import fr.cpe.scoobygang.common.model.enumeration.ROOM_STATUS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User owner;

    private ROOM_STATUS status;

    private double bet;
}
