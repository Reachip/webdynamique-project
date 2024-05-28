package fr.cpe.scoobygang.common.dto.mapper;

import fr.cpe.scoobygang.common.dto.request.RoomCreateRequest;
import fr.cpe.scoobygang.common.dto.response.RoomResponse;
import fr.cpe.scoobygang.common.model.Room;
import fr.cpe.scoobygang.common.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper( RoomMapper.class );
    Room roomToRoomCreateRequest(RoomCreateRequest room);

    @Mapping(source = "owner", target = "userId", qualifiedByName = "ownerToUserId")
    RoomCreateRequest roomCreateRequestToRoom(Room room);
    List<RoomCreateRequest> roomCreateRequestsToRooms(List<Room> rooms);
    @Named("ownerToUserId")
    static int ownerToUserId(User owner) {
        if (owner == null) {
            return 0;
        }
        return owner.getId();
    }

    RoomResponse roomToRoomResponse(Room room);
}
