package com.parser.scraper.controllers;

import com.parser.scraper.model.MarketPlace;
import com.parser.scraper.repository.MarketPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    private final MarketPlaceRepository marketPlaceRepository;

    @GetMapping("test")
    public String test(@RequestParam(required = false, defaultValue = "lootfarm,tradeit") List<String> service, Model model){

        MarketPlace firstMarketPlace;
        MarketPlace secondMarketPlace;
        List<MarketPlace> marketPlaces = marketPlaceRepository.findAll();
        if (    marketPlaces.stream().filter(object -> object.getName().equals(service.get(0))).findFirst().isPresent() &&
                marketPlaces.stream().filter(object -> object.getName().equals(service.get(1))).findFirst().isPresent()){
            firstMarketPlace = marketPlaces.stream().filter(object -> object.getName().equals(service.get(0))).findFirst().get();
            secondMarketPlace = marketPlaces.stream().filter(object -> object.getName().equals(service.get(1))).findFirst().get();
        }else {
            firstMarketPlace = marketPlaces.stream().filter(object -> object.getName().equals("lootfarm")).findFirst().get();
            secondMarketPlace = marketPlaces.stream().filter(object -> object.getName().equals("tradeit")).findFirst().get();
        }

        model.addAttribute("firstService", firstMarketPlace.getName().toUpperCase());
        model.addAttribute("secondService", secondMarketPlace.getName().toUpperCase());
        return "tempHtml";
    }
}
