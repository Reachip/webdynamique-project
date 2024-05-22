package fr.cpe.scoobygang.atelier2.mapper;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.Store;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.request.CardRequest;
import fr.cpe.scoobygang.atelier2.request.CardResponse;
import fr.cpe.scoobygang.atelier2.request.UserRequest;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CardMapper {
    CardMapper INSTANCE = Mappers.getMapper( CardMapper.class );
    void updateCard(@MappingTarget Card card, CardRequest cardRequest);
    Card cardRequestToCard(CardRequest car);

    @Mapping(source = "owner", target = "userId", qualifiedByName = "ownerToUserId")
    @Mapping(source = "store", target = "storeId", qualifiedByName = "storeToStoreId")
    CardResponse cardToCardResponse(Card card);

    List<CardResponse> cardsToCardResponses(List<Card> cards);

    @Named("ownerToUserId")
    public static int ownerToUserId(User owner) {
        if (owner == null) {
            return 0;
        }
        return owner.getId();
    }

    @Named("storeToStoreId")
    public static int storeToStoreId(Store store) {
        if (store == null) {
            return 0;
        }
        return store.getId();
    }
}
