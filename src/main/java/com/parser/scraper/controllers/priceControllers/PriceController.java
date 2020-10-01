package com.parser.scraper.controllers.priceControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PriceController {

    @GetMapping("/prices")
    public String trade(Model model) {
        model.addAttribute("title", "Price list");

        return "prices";
    }
}
