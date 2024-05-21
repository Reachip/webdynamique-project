package fr.cpe.scoobygang.atelier2.mapper;

import fr.cpe.scoobygang.atelier2.request.UserRequest;
import fr.cpe.scoobygang.atelier2.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
    User userRequestToUser(UserRequest car);
    UserRequest userToUserRequest(User car);
}
