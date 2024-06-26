package fr.cpe.scoobygang.atelier3.api_room_microservice.service;

import fr.cpe.scoobygang.common.dto.mapper.RoomMapper;
import fr.cpe.scoobygang.common.dto.request.PutCardRoomRequest;
import fr.cpe.scoobygang.common.dto.request.RoomCreateRequest;
import fr.cpe.scoobygang.common.dto.request.UserRequest;
import fr.cpe.scoobygang.common.dto.response.RoomResponse;
import fr.cpe.scoobygang.common.model.Card;
import fr.cpe.scoobygang.common.model.Room;
import fr.cpe.scoobygang.common.model.User;
import fr.cpe.scoobygang.common.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomResponse> getRooms() {
        return RoomMapper.INSTANCE.roomsToRoomResponses(roomRepository.findAll().stream()
                .filter(room -> room.getChallenger() == null)
                .collect(Collectors.toList()));
    }

    public Room getRoom(Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        return roomOptional.orElse(null);
    }

    public Room createRoom(RoomCreateRequest room) {
        Room roomToModel = RoomMapper.INSTANCE.roomToRoomCreateRequest(room);
        return roomRepository.save(roomToModel);
    }

    public Room joinRoom(User user, Long roomId) {
        Optional<Room> optRoom = roomRepository.findById(roomId);
        if (optRoom.isPresent()){
            Room room = optRoom.get();
            room.setChallenger(user);
            return roomRepository.save(room);
        }
        throw new RuntimeException();
    }


    public RoomResponse updateCard(User user, PutCardRoomRequest cardRoomRequest) {
        Optional<Room> roomOptional = roomRepository.findById(cardRoomRequest.getRoomId());
        return roomOptional.map(RoomMapper.INSTANCE::roomToRoomResponse).orElse(null);
    }
}
