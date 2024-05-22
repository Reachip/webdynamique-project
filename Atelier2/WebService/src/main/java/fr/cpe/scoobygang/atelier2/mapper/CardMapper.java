package fr.cpe.scoobygang.atelier2.mapper;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.request.CardRequest;
import fr.cpe.scoobygang.atelier2.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {
    CardMapper INSTANCE = Mappers.getMapper( CardMapper.class );
    void updateCard(@MappingTarget Card card, CardRequest cardRequest);
    Card cardRequestToCard(CardRequest car);
}
