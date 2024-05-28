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

    RoomResponse roomToRoomResponse(Room room);

    List<RoomResponse> roomsToRoomResponses(List<Room> rooms);
}
