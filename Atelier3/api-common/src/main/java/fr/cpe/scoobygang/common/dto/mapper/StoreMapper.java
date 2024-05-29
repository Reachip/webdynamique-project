package fr.cpe.scoobygang.common.dto.mapper;

import fr.cpe.scoobygang.common.dto.response.StoreResponse;
import fr.cpe.scoobygang.common.model.Card;
import fr.cpe.scoobygang.common.model.Store;
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


    List<StoreResponse> storesToStoreResponses (List<Store> stores);

    Store StoreResponsesToStore (StoreResponse storeResponse);

    StoreResponse storeToStoreResponse(Store store);


    @Named("cardListToCardCount")
    static int cardListToCardCount(List<Card> cardList) {
        return cardList.size();
    }
}
