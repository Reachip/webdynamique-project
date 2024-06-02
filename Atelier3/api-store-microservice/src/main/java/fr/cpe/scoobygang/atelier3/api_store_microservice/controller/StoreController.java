package fr.cpe.scoobygang.atelier3.api_store_microservice.controller;

import fr.cpe.scoobygang.common.dto.mapper.CardMapper;
import fr.cpe.scoobygang.common.dto.mapper.StoreMapper;
import fr.cpe.scoobygang.common.dto.request.StoreOrderRequest;
import fr.cpe.scoobygang.atelier3.api_store_microservice.service.StoreService;
import fr.cpe.scoobygang.common.dto.response.CardResponse;
import fr.cpe.scoobygang.common.dto.response.StoreResponse;
import fr.cpe.scoobygang.common.jwt.JWT;
import fr.cpe.scoobygang.common.jwt.JWTService;
import fr.cpe.scoobygang.common.model.Store;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.cpe.scoobygang.common.model.Card;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/store")
@CrossOrigin(origins = "*")
public class StoreController {
    private final StoreService storeService;
    private final JWTService jwtService;

    public StoreController(JWTService jwtService, StoreService storeService) {
        this.jwtService = jwtService;
        this.storeService = storeService;
    }

    @GetMapping(value = {""})
    public ResponseEntity<List<StoreResponse>> getStores() {
        return ResponseEntity.ok(StoreMapper.INSTANCE.storesToStoreResponses(storeService.getStores()));
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<StoreResponse> getStores(@PathVariable int id) {
        return ResponseEntity.ok(StoreMapper.INSTANCE.storeToStoreResponse(storeService.getStore(id)));
    }

    @PostMapping(value = {"/buy"})
    public ResponseEntity<HttpStatus> buyCard(@RequestHeader(value = "Authorization") String authorization, @RequestBody StoreOrderRequest storeOrderRequest) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);

        if (jwt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        int userID = jwt.get().getJwtInformation().getUserID();

        if (!storeService.buyCard(authorization,storeOrderRequest.getCardId(), userID, storeOrderRequest.getStoreId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = {"/{id}/cards"})
    public ResponseEntity<List<CardResponse>> sellCard(@PathVariable int id, @RequestHeader(value = "Authorization") String authorization) {
        return ResponseEntity.ok(CardMapper.INSTANCE.cardsToCardResponses(storeService.getCardsById(authorization, id)));
    }

    @PostMapping(value = {"/sell"})
    public ResponseEntity<ResponseStatus> sellCard(@RequestHeader(value = "Authorization") String authorization, @RequestBody StoreOrderRequest storeOrderRequest) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);
        if (jwt.isPresent()) {
            List<Card> cards = storeService.getCardsForUser(authorization);
            if (cards.stream().anyMatch(c -> c.getId() == storeOrderRequest.getCardId()) && storeService.sellUserCard(authorization, storeOrderRequest.getCardId(), storeOrderRequest.getStoreId())) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping(value = {"/sell/cancel"})
    public ResponseEntity<ResponseStatus> cancelSellCard(@RequestHeader(value = "Authorization") String authorization, @RequestBody StoreOrderRequest storeOrderRequest) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);
        if (jwt.isPresent()) {
            List<Card> cards = storeService.getCardsForUser(authorization);
            if (cards.stream().anyMatch(c -> c.getId() == storeOrderRequest.getCardId()) && storeService.cancelSellCard(authorization, storeOrderRequest.getCardId(), storeOrderRequest.getStoreId())) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
