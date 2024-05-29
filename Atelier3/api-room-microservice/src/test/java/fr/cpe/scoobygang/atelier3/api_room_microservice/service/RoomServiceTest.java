package fr.cpe.scoobygang.atelier3.api_room_microservice.service;

import fr.cpe.scoobygang.common.dto.mapper.RoomMapper;
import fr.cpe.scoobygang.common.dto.request.RoomCreateRequest;
import fr.cpe.scoobygang.common.dto.response.RoomResponse;
import fr.cpe.scoobygang.common.model.Room;
import fr.cpe.scoobygang.common.model.User;
import fr.cpe.scoobygang.common.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getRooms() {
        Room room1 = new Room();
        room1.setId(1L);
        room1.setName("Room 1");
        room1.setBet(100);
        room1.setOwner(new User());
        room1.setChallenger(null); // Available room

        Room room2 = new Room();
        room2.setId(2L);
        room2.setName("Room 2");
        room2.setBet(200);
        room2.setOwner(new User());
        room2.setChallenger(null); // Available room

        Room room3 = new Room();
        room3.setId(3L);
        room3.setName("Room 3");
        room3.setBet(300);
        room3.setOwner(new User());
        room3.setChallenger(new User()); // Not available room

        List<Room> rooms = Arrays.asList(room1, room2, room3);

        when(roomRepository.findAll()).thenReturn(rooms);

        List<RoomResponse> availableRooms = roomService.getRooms();

        assertEquals(2, availableRooms.size());
        assertEquals("Room 1", availableRooms.get(0).getName());
        assertEquals("Room 2", availableRooms.get(1).getName());
    }

    @Test
    void getRoom_ShouldReturnRoom_WhenRoomExists() {
        Long roomId = 1L;
        Room room = new Room();
        room.setId(roomId);
        room.setName("Test Room");

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        Room foundRoom = roomService.getRoom(roomId);

        assertEquals(roomId, foundRoom.getId());
        assertEquals("Test Room", foundRoom.getName());
    }

    @Test
    void getRoom_ShouldReturnNull_WhenRoomDoesNotExist() {
        Long roomId = 1L;

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        Room foundRoom = roomService.getRoom(roomId);

        assertNull(foundRoom);
    }

    @Test
    void createRoom_ShouldReturnSavedRoom() {
        // Arrange
        RoomCreateRequest roomCreateRequest = new RoomCreateRequest();
        roomCreateRequest.setName("Test Room");
        roomCreateRequest.setBet(100);

        Room roomToSave = new Room();
        roomToSave.setName("Test Room");
        roomToSave.setBet(100);

        Room savedRoom = new Room();
        savedRoom.setId(1L);
        savedRoom.setName("Test Room");
        savedRoom.setBet(100);

        // Mock the mapping and repository save behavior
        when(RoomMapper.INSTANCE.roomToRoomCreateRequest(roomCreateRequest)).thenReturn(roomToSave);
        when(roomRepository.save(any())).thenReturn(savedRoom);

        // Act
        Room result = roomService.createRoom(roomCreateRequest);

        // Assert
        assertEquals(savedRoom.getId(), result.getId());
        assertEquals(savedRoom.getName(), result.getName());
        assertEquals(savedRoom.getBet(), result.getBet());
    }

    @Test
    void joinRoom_ShouldUpdateChallenger_WhenRoomExists() {
        Long roomId = 1L;
        User challenger = new User();
        challenger.setId(2);
        challenger.setName("Test User");

        Room room = new Room();
        room.setId(roomId);
        room.setName("Test Room");

        Room updatedRoom = new Room();
        updatedRoom.setId(roomId);
        updatedRoom.setName("Test Room");
        updatedRoom.setChallenger(challenger);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(updatedRoom);

        Room result = roomService.joinRoom(challenger, roomId);

        assertEquals(roomId, result.getId());
        assertEquals("Test User", result.getChallenger().getName());
    }

    @Test
    void joinRoom_ShouldThrowException_WhenRoomDoesNotExist() {
        Long roomId = 1L;
        User challenger = new User();
        challenger.setId(2);
        challenger.setName("Test User");

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            roomService.joinRoom(challenger, roomId);
        });
    }
}