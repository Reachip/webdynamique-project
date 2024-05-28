package fr.cpe.scoobygang.common.runner;

import fr.cpe.scoobygang.common.model.Room;
import fr.cpe.scoobygang.common.model.User;
import fr.cpe.scoobygang.common.repository.RoomRepository;
import fr.cpe.scoobygang.common.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Order(4)
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
       userRepository.findAll().forEach(users::add);

       Collections.shuffle(users);

       List<Room> rooms = users.stream()
               .limit(5)
               .map(Room::random)
               .toList();

       roomRepository.saveAll(rooms);
    }
}
