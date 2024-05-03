package fr.cpe.scoobygang.atelier1.controller;

import fr.cpe.scoobygang.atelier1.model.CardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RequestCtrl {
    @Autowired
    CardDao cardDao;

    @RequestMapping(value = {"/view"}, method = RequestMethod.GET)
    public String viewCard(Model model) {
        model.addAttribute("card", cardDao.getRandomCard());
        return "cardView";
    }
}
