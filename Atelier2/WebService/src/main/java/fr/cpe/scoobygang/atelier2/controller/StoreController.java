package fr.cpe.scoobygang.atelier2.controller;

import fr.cpe.scoobygang.atelier2.mapper.CardMapper;
import fr.cpe.scoobygang.atelier2.mapper.StoreMapper;
import fr.cpe.scoobygang.atelier2.request.CardResponse;
import fr.cpe.scoobygang.atelier2.request.StoreOrderRequest;
import fr.cpe.scoobygang.atelier2.request.StoreResponse;
import fr.cpe.scoobygang.atelier2.runner.StoreApplicationRunner;
import fr.cpe.scoobygang.atelier2.security.JWT;
import fr.cpe.scoobygang.atelier2.security.JWTService;
import fr.cpe.scoobygang.atelier2.service.CardService;
import fr.cpe.scoobygang.atelier2.service.StoreService;
import fr.cpe.scoobygang.atelier2.service.TransactionService;
import fr.cpe.scoobygang.atelier2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class StoreController {
    private final CardService cardService;
    private final UserService userService;
    private final StoreService storeService;
    private final JWTService jwtService;


    private final StoreApplicationRunner storeApplicationRunner;

    public StoreController(JWTService jwtService, CardService cardService, UserService userService, StoreService storeService, StoreApplicationRunner storeApplicationRunner, TransactionService transactionService) {
        this.jwtService = jwtService;
        this.cardService = cardService;
        this.userService = userService;
        this.storeService = storeService;
        this.storeApplicationRunner = storeApplicationRunner;
    }

    @GetMapping(value = {"/stores"})
    public ResponseEntity<List<StoreResponse>> getStores() {
        return ResponseEntity.ok(StoreMapper.INSTANCE.storesToStoreResponses(storeService.getStores()));
    }

    @PostMapping(value = {"/store/buy"})
    public ResponseEntity<HttpStatus> buyCard(@RequestHeader(value = "Authorization") String authorization, @RequestBody StoreOrderRequest storeOrderRequest) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);
        if (jwt.isPresent()) {
            int userID = jwt.get().getJwtInformation().getUserID();
            if (storeService.buyCard(storeOrderRequest.getCardId(), userID, storeOrderRequest.getStoreId())) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping(value = {"/store/{id}/cards"})
    public ResponseEntity<List<CardResponse>> sellCard(@PathVariable int id) {
        return ResponseEntity.ok(CardMapper.INSTANCE.cardsToCardResponses(storeService.getCardsById(id)));
    }

    @PostMapping(value = {"/store/sell"})
    public ResponseEntity<ResponseStatus> sellCard(@RequestHeader(value = "Authorization") String authorization, @RequestBody StoreOrderRequest storeOrderRequest) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);
        if (jwt.isPresent()) {
            int userID = jwt.get().getJwtInformation().getUserID();
            if (cardService.getAllUserCard(userID).stream().anyMatch(c -> c.getId() == storeOrderRequest.getCardId()) && storeService.sellUserCard(storeOrderRequest.getCardId(), storeOrderRequest.getStoreId())) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping(value = {"/store/sell/cancel"})
    public ResponseEntity<ResponseStatus> cancelSellCard(@RequestHeader(value = "Authorization") String authorization, @RequestBody StoreOrderRequest storeOrderRequest) {
        Optional<JWT> jwt = jwtService.fromAuthorization(authorization);
        if (jwt.isPresent()) {
            int userID = jwt.get().getJwtInformation().getUserID();
            if (cardService.getAllUserCard(userID).stream().anyMatch(c -> c.getId() == storeOrderRequest.getCardId()) && storeService.cancelSellCard(storeOrderRequest.getCardId(), storeOrderRequest.getStoreId())) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
