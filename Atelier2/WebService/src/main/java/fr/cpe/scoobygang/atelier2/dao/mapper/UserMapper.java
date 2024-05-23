package fr.cpe.scoobygang.atelier2.dao.mapper;

import fr.cpe.scoobygang.atelier2.dao.request.UserPutRequest;
import fr.cpe.scoobygang.atelier2.dao.request.UserRequest;
import fr.cpe.scoobygang.atelier2.dao.response.UserResponse;
import fr.cpe.scoobygang.atelier2.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
    User userRequestToUser(UserRequest car);
    User userPutRequestToUser(UserPutRequest userPutRequest);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "balance", source = "balance")
    void userApply(@MappingTarget User userToUpdate, User user);
    UserRequest userToUserRequest(User car);
    UserResponse userToUserResponse(User user);

    List<UserResponse> usersToUserResponses(List<User> users);
}
