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

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User challenger;

    private ROOM_STATUS status = ROOM_STATUS.WAIT_FOR_PLAYER ;

    private double bet;

    public static Room random(User owner) {
        int randomBet = (int)Math.floor(Math.random() * (50 - 5 + 1) + 5);

        Room room = new Room();
        room.setBet(randomBet);

        room.setName(owner.getName() + "'s Room");
        room.setOwner(owner);

        return room;
    }
}
