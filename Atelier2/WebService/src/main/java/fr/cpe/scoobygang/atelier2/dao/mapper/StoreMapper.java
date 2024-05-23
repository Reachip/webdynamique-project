package fr.cpe.scoobygang.atelier2.dao.mapper;

import fr.cpe.scoobygang.atelier2.dao.response.StoreResponse;
import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.Store;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StoreMapper {
    StoreMapper INSTANCE = Mappers.getMapper( StoreMapper.class );

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(source = "cardList", target = "cardCount", qualifiedByName = "cardListToCardCount")
    StoreResponse storeToStoreResponse(Store store);

    List<StoreResponse> storesToStoreResponses (List<Store> stores);

    @Named("cardListToCardCount")
    static int cardListToCardCount(List<Card> cardList) {
        return cardList.size();
    }
}
