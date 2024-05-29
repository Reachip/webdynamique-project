package fr.cpe.scoobygang.common.model;

import com.github.javafaker.Faker;
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
    @JoinColumn(name = "challengerId", referencedColumnName = "id")
    private User challenger;

    @ManyToOne
    @JoinColumn(name = "ownerCardId", referencedColumnName = "id")
    private Card ownerCard;

    @ManyToOne
    @JoinColumn(name = "challengerCardId", referencedColumnName = "id")
    private Card challengerCard;

    private double bet;

    public static Room random(User owner) {
        int randomBet = (int)Math.floor(Math.random() * (50 - 5 + 1) + 5);

        Room room = new Room();
        room.setBet(randomBet);

        Faker faker = new Faker();
        String roomName = faker.animal().name();
        roomName = roomName.substring(0, 1).toUpperCase() + roomName.substring(1);

        room.setName(roomName + "'s Room");
        room.setOwner(owner);

        return room;
    }
}
