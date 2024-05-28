package fr.cpe.scoobygang.atelier3.api_room_microservice.controller;

import fr.cpe.scoobygang.common.dto.request.RoomCreateRequest;
import fr.cpe.scoobygang.common.model.Room;
import fr.cpe.scoobygang.common.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomControllerTest {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomController roomController;

    @Test
    void getRoomsIsOk() {
        roomRepository.saveAll(List.of(new Room(), new Room()));

        ResponseEntity<List<RoomCreateRequest>> rooms = roomController.getRooms();

        assertEquals(HttpStatusCode.valueOf(200), rooms.getStatusCode());
        assertEquals(2, Objects.requireNonNull(rooms.getBody()).size());
    }

    @Test
    void createRoomIsOk() {

        assertEquals(roomRepository.findAll().stream().findFirst().get().getName(), "Room 1");
    }
}