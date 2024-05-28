package fr.cpe.scoobygang.atelier3.api_room_microservice.controller;

import fr.cpe.scoobygang.atelier3.api_room_microservice.service.RoomService;
import fr.cpe.scoobygang.common.dto.request.RoomCreateRequest;
import fr.cpe.scoobygang.common.model.Room;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class RoomController {
    private final RoomService roomService;
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomCreateRequest>> getRooms() {
        return ResponseEntity.ok(roomService.getRooms());
    }

    @PostMapping("/room")
    public ResponseEntity<Room> createRoom(@RequestBody RoomCreateRequest request) {
        return ResponseEntity.ok(roomService.createRoom(request));
    }
}
