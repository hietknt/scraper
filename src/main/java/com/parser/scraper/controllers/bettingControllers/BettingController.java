package com.parser.scraper.controllers.bettingControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bet")
public class BettingController {

    @GetMapping
    public String betting() {
        return "bets";
    }
}
