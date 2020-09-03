package com.parser.scraper.controllers.tradeControllers;

import com.parser.scraper.model.ComparedItem;
import com.parser.scraper.model.GameInfo;
import com.parser.scraper.model.Items;
import com.parser.scraper.model.MarketPlace;
import com.parser.scraper.repository.GameInfoRepository;
import com.parser.scraper.repository.ItemsRepository;
import com.parser.scraper.repository.MarketPlaceRepository;
import com.parser.scraper.service.comparator.TradeComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping(value = "/trade")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TradeController {

    private final MarketPlaceRepository marketPlaceRepository;
    private final ItemsRepository itemsRepository;
    private final GameInfoRepository gameInfoRepository;
    private final TradeComparator tradeComparator;

    @GetMapping("")
    private String redirectFirst(){
        return "redirect:trade/csgo";
    }

    @GetMapping("/")
    private String redirectSecond(){
        return "redirect:csgo";
    }

    @GetMapping("/{game}")
    private String compareCsgo(@RequestParam(required = false, defaultValue = "5.0") double minPrice,
                               @RequestParam(required = false, defaultValue = "50.0") double maxPrice,
                               @RequestParam(required = false, defaultValue = "false") boolean isOverStocked,
                               @RequestParam(required = false, defaultValue = "lootfarm,tradeit") List<String> service,
                               @RequestParam(required = false, defaultValue = "none") String order,
                               @PathVariable String game,
                               Model model){

        long gameId = 730; //default - csgo
        List<GameInfo> games = gameInfoRepository.findAll();
        if (games.stream().filter(object -> object.getGameName().equals(game)).findFirst().isPresent()){
            gameId = games.stream().filter(object -> object.getGameName().equals(game)).findFirst().get().getGameId();
        }

        return compare(minPrice, maxPrice, isOverStocked, service, order, gameId, model);
    }

    private String compare(double minPrice, double maxPrice, boolean isOverStocked, List<String> service, String order, long gameId, Model model){
        if (service.size() == 1){
            return oneServicePage(minPrice, maxPrice, isOverStocked, service.get(0), model);
        }

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


        Set<Items> firstMarketSet = itemsRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThan((int) firstMarketPlace.getId(), gameId, maxPrice, minPrice);
        Set<Items> secondMarketSet = itemsRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThan((int) secondMarketPlace.getId(), gameId, maxPrice, minPrice);
        List<ComparedItem> comparedItems = new LinkedList<>();

        firstMarketSet.stream().forEach(firstMarketItem -> {
            if (secondMarketSet.contains(firstMarketItem)) {
                Items secondMarketItem = secondMarketSet.stream().filter(firstMarketItem::equals).findAny().get();
                comparedItems.add(new ComparedItem(firstMarketItem, secondMarketItem));
            }
        });

        if (order.equals("first_second")){
            Collections.sort(comparedItems, tradeComparator.getFirstToSecond());
        }else if(order.equals("second_first")){
            Collections.sort(comparedItems, tradeComparator.getSecondToFirst());
        }else if(order.equals("first_price_asc")){
            Collections.sort(comparedItems, tradeComparator.getFirstServicePrice());
        }else if(order.equals("second_price_asc")){
            Collections.sort(comparedItems, tradeComparator.getSecondServicePrice());
        }

        model.addAttribute("title", "Compare CSGO");
        model.addAttribute("firstService", firstMarketPlace.getName().toUpperCase());
        model.addAttribute("secondService", secondMarketPlace.getName().toUpperCase());
        model.addAttribute("comparedItem", comparedItems);

        return "trade";
    }

    private String oneServicePage(double minPrice, double maxPrice, boolean isOverStocked, String service, Model model){
        model.addAttribute("title", service + " info");

        return "trade";
    }
}
