package fr.cpe.scoobygang.atelier3.api_room_microservice.service;

import fr.cpe.scoobygang.common.dto.mapper.RoomMapper;
import fr.cpe.scoobygang.common.dto.request.RoomCreateRequest;
import fr.cpe.scoobygang.common.dto.request.UserRequest;
import fr.cpe.scoobygang.common.model.Room;
import fr.cpe.scoobygang.common.model.User;
import fr.cpe.scoobygang.common.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomCreateRequest> getRooms() {
        return roomRepository.findAll().stream().map(RoomMapper.INSTANCE::roomCreateRequestToRoom).toList();
    }

    public Room createRoom(RoomCreateRequest room) {
        Room roomToModel = RoomMapper.INSTANCE.roomToRoomCreateRequest(room);
        return roomRepository.save(roomToModel);
    }

    public Room joinRoom(UserRequest user, Long roomId) {
        Room room = roomRepository.findById(roomId).get();

        //room.setChallenger(user);
        return roomRepository.save(room);
    }
}
