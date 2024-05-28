package fr.cpe.scoobygang.atelier3.api_room_microservice.runner;

import fr.cpe.scoobygang.common.model.Room;
import fr.cpe.scoobygang.common.model.User;
import fr.cpe.scoobygang.common.repository.RoomRepository;

import fr.cpe.scoobygang.common.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RoomApplicationRunner implements ApplicationRunner {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomApplicationRunner(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (roomRepository.count() > 0)
            return;

       List<User> users = new ArrayList<>();
       Iterable<User> usersIterable = userRepository.findAll();

       usersIterable.forEach(users::add);

       Collections.shuffle(users);
       users = users.stream().limit(5).toList();

       List<Room> rooms = users.stream().map(Room::random).toList();
       roomRepository.saveAll(rooms);
    }
}
