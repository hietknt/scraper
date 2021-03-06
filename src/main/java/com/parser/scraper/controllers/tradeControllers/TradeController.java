package com.parser.scraper.controllers.tradeControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TradeController {

    @GetMapping("/trade")
    public String trade(Model model) {
        model.addAttribute("title", "Trade");

        return "trade";
    }
}
