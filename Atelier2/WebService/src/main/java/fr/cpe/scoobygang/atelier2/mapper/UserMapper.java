package fr.cpe.scoobygang.atelier2.mapper;

import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.request.UserPutRequest;
import fr.cpe.scoobygang.atelier2.request.UserRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
    User userRequestToUser(UserRequest car);
    User userPutRequestToUser(UserPutRequest userPutRequest);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "surName", source = "surName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "account", source = "account")
    void userApply(@MappingTarget User userToUpdate, User user);
    UserRequest userToUserRequest(User car);
}
