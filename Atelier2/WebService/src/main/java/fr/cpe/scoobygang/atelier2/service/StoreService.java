package fr.cpe.scoobygang.atelier2.service;

import fr.cpe.scoobygang.atelier2.model.Card;
import fr.cpe.scoobygang.atelier2.model.User;
import fr.cpe.scoobygang.atelier2.repository.CardRepository;
import fr.cpe.scoobygang.atelier2.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    public StoreService(UserRepository userRepository, CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public void sellUserCard(int cardId, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id " + userId));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found for id " + cardId));

        if (!user.getCards().contains(card)) {
            throw new IllegalStateException("Card not owned by the user.");
        }

        // Retirer la carte de la liste de l'utilisateur
        user.getCards().remove(card);
        card.setOwner(null); // Mise à jour de la relation à null

        // Sauvegarder les modifications
        userRepository.save(user);
        cardRepository.save(card);
    }

    public void buyCard(int cardId, int userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id " + userId));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found for id " + cardId));

        card.setOwner(user);
        user.getCards().add(card);

        cardRepository.save(card);
        userRepository.save(user);
    }
}
