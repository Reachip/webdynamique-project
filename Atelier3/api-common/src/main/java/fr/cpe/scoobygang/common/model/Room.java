package fr.cpe.scoobygang.common.model;

import com.github.javafaker.Faker;
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
