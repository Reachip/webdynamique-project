package fr.cpe.scoobygang.atelier2.service;

import fr.cpe.scoobygang.atelier2.dao.request.CardRequest;
import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    @Mock
    private  CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @Test
    void getAllUserCardIsOk() {
        // Given
        Card card1 = new Card();
        card1.setDescription("card 1");

        // When
        Mockito.when(cardRepository.findByOwnerId(1)).thenReturn(List.of(card1));

        // Then
        List<Card> cards = cardService.getAllUserCard(1);
        Mockito.verify(cardRepository, Mockito.times(1)).findByOwnerId(1);
        assertEquals("card 1", cards.stream().findFirst().get().getDescription());
    }

    @Test
    void saveCards() {
        // Given
        Card card1 = new Card();
        card1.setDescription("card 1");

        Card card2 = new Card();
        card2.setDescription("card 2");

        List<Card> cardsToSave = List.of(card1, card2);

        // Then
        cardService.saveCards(cardsToSave);

        Mockito.verify(cardRepository, Mockito.times(1)).saveAll(cardsToSave);
    }

    @Test
    void saveCard() {
        CardRequest cardRequest = new CardRequest();
        cardRequest.setName("Card 1");

        cardService.saveCard(cardRequest);

        Mockito.verify(cardRepository, Mockito.times(1)).save(Mockito.any(Card.class));

    }

    @Test
    void updateCard() {
        CardRequest cardRequest = new CardRequest();
        cardRequest.setName("Card 2");

        Card card = new Card();
        card.setName("Card 1");

        Mockito.when(cardRepository.findById(1)).thenReturn(Optional.of(card));

        Optional<Card> updatedCard = cardService.updateCard(1, cardRequest);
        Mockito.verify(cardRepository, Mockito.times(1)).save(Mockito.any());

        assertTrue(updatedCard.isPresent());
        assertEquals(card.getName(), updatedCard.get().getName());
    }

    @Test
    void updateCardWhenCardIsNotFound() {
        CardRequest cardRequest = new CardRequest();
        cardRequest.setName("Card 2");

        Card card = new Card();
        card.setName("Card 1");

        Mockito.when(cardRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Card> updatedCard = cardService.updateCard(1, cardRequest);
        Mockito.verify(cardRepository, Mockito.times(0)).save(Mockito.any());

        assertTrue(updatedCard.isEmpty());
    }

    @Test
    void getCard() {
        cardService.getCard(1);
        Mockito.verify(cardRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void getCards() {
        Mockito.when(cardRepository.findAll()).thenReturn(List.of(new Card(), new Card()));

        assertEquals(2, cardService.getCards().size());
        Mockito.verify(cardRepository, Mockito.times(1)).findAll();
    }

    @Test
    void deleteCardIsOk() {
        Mockito.when(cardRepository.findById(1)).thenReturn(Optional.of(new Card()));

        cardService.deleteCard(1);
        Mockito.verify(cardRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    void deleteCardIsNotOk() {
        Mockito.when(cardRepository.findById(1)).thenReturn(Optional.empty());

        cardService.deleteCard(1);
        Mockito.verify(cardRepository, Mockito.times(0)).delete(Mockito.any());
    }

    @Test
    void attachUserToCard() {
    }
}