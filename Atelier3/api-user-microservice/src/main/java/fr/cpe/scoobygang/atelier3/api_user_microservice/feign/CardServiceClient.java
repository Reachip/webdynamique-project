package fr.cpe.scoobygang.atelier3.api_user_microservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "card-service", url = "http://localhost:8082")
public interface CardServiceClient {

    @PostMapping("/cards/attach/user/{id}")
    ResponseEntity<HttpStatus> attachCardsToUser(@PathVariable("id") Integer userId,
                                                 @RequestHeader("Authorization") String authorization);
}