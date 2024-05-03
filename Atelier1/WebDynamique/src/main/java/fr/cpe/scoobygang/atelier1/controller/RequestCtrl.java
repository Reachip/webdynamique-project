package fr.cpe.scoobygang.atelier1.controller;

import fr.cpe.scoobygang.atelier1.model.Card;
import fr.cpe.scoobygang.atelier1.model.CardFormDto;
import fr.cpe.scoobygang.atelier1.dao.CardDao;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RequestCtrl {
    @Autowired
    private CardDao cardDao;
    @PostConstruct
    public void init() {
        cardDao.createCardList();
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = {"/view"}, method = RequestMethod.GET)
    public String viewCard(Model model) {
        model.addAttribute("card", cardDao.getRandomCard());
        return "cardView";
    }

    // formular to add a card
    @RequestMapping(value = {"/addCard"}, method = RequestMethod.GET)
    public String addCard(Model model) {
        model.addAttribute("cardForm", new CardFormDto());
        return "addCardView";
    }

    // Add a card action
    @RequestMapping(value = {"/addCard"}, method = RequestMethod.POST)
    public String addCard(Model model, @ModelAttribute("cardForm") CardFormDto cardFormDto) {
        Card card = cardDao.addCard(cardFormDto);
        model.addAttribute("card", card);
        return "cardView";
    }

}
