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

import java.util.*;
import java.util.stream.Stream;


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
                               @RequestParam(required = false, defaultValue = "0") int firstServiceMinCount,
                               @RequestParam(required = false, defaultValue = "10000") int firstServiceMaxCount,
                               @RequestParam(required = false, defaultValue = "0") int secondServiceMinCount,
                               @RequestParam(required = false, defaultValue = "10000") int secondServiceMaxCount,
                               @RequestParam(required = false, defaultValue = "-10000") double firstToSecondMinPerCent,
                               @RequestParam(required = false, defaultValue = "10000") double firstToSecondMaxPerCent,
                               @RequestParam(required = false, defaultValue = "-10000") double secondToFirstMinPerCent,
                               @RequestParam(required = false, defaultValue = "10000") double secondToFirstMaxPerCent,
                               @RequestParam(required = false, defaultValue = "") String itemName,
                               @PathVariable String game,
                               Model model){

        long gameId = 730; //default - csgo
        List<GameInfo> games = gameInfoRepository.findAll();
        if (games.stream().filter(object -> object.getGameName().equals(game)).findFirst().isPresent()){
            gameId = games.stream().filter(object -> object.getGameName().equals(game)).findFirst().get().getGameId();
        }

        if (firstServiceMinCount < 0){ firstServiceMinCount = 0; }
        if (firstServiceMaxCount < 0){ firstServiceMaxCount = 10000; }
        if (secondServiceMinCount < 0){ secondServiceMinCount = 0; }
        if (secondServiceMaxCount < 0){ secondServiceMaxCount = 10000; }

        return compare(minPrice, maxPrice, isOverStocked, service, order,
                firstServiceMinCount, firstServiceMaxCount,
                secondServiceMinCount, secondServiceMaxCount,
                firstToSecondMinPerCent, firstToSecondMaxPerCent,
                secondToFirstMinPerCent, secondToFirstMaxPerCent,
                itemName.toLowerCase(), gameId, model);
    }

    private String compare(double minPrice, double maxPrice, boolean isOverStocked, List<String> service, String order,
                           int firstServiceMinCount, int firstServiceMaxCount,
                           int secondServiceMinCount, int secondServiceMaxCount,
                           double firstToSecondMinPerCent, double firstToSecondMaxPerCent,
                           double secondToFirstMinPerCent, double secondToFirstMaxPerCent,
                           String itemName, long gameId, Model model){
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

        Set<Items> firstMarketSet;
        Set<Items> secondMarketSet;
        if (isOverStocked == true && order.equals("first_second")){
            firstMarketSet = itemsRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqual((int) firstMarketPlace.getId(), gameId, maxPrice, minPrice, firstServiceMinCount, firstServiceMaxCount);
            secondMarketSet = itemsRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqualAndAmountLessThenEqualMax((int) secondMarketPlace.getId(), gameId, maxPrice, minPrice, secondServiceMinCount, secondServiceMaxCount);
        }else if(isOverStocked == true && order.equals("second_first")){
            firstMarketSet = itemsRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqualAndAmountLessThenEqualMax((int) firstMarketPlace.getId(), gameId, maxPrice, minPrice, firstServiceMinCount, firstServiceMaxCount);
            secondMarketSet = itemsRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqual((int) secondMarketPlace.getId(), gameId, maxPrice, minPrice, secondServiceMinCount, secondServiceMaxCount);
        }else {
            firstMarketSet = itemsRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqual((int) firstMarketPlace.getId(), gameId, maxPrice, minPrice, firstServiceMinCount, firstServiceMaxCount);
            secondMarketSet = itemsRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqual((int) secondMarketPlace.getId(), gameId, maxPrice, minPrice, secondServiceMinCount, secondServiceMaxCount);
        }

        List<ComparedItem> comparedItems = new LinkedList<>();
        firstMarketSet.stream().forEach(firstMarketItem -> {
            if (secondMarketSet.contains(firstMarketItem)) {
                if (Stream.of(itemName.split(" ")).allMatch(firstMarketItem.getName().toLowerCase()::contains)) {
                    Items secondMarketItem = secondMarketSet.stream().filter(firstMarketItem::equals).findAny().get();
                    ComparedItem item = new ComparedItem(firstMarketItem, secondMarketItem);
                    if(firstToSecondMinPerCent <= item.getFirstToSecondProfit() && item.getFirstToSecondProfit() <= firstToSecondMaxPerCent
                            && secondToFirstMinPerCent <= item.getSecondToFirstProfit() && item.getSecondToFirstProfit() <= secondToFirstMaxPerCent) {
                        comparedItems.add(item);
                    }
                }
            }
        });

        if (order.equals("first_second")){
            Collections.sort(comparedItems, tradeComparator.getFirstToSecond());
        }else if(order.equals("second_first")){
            Collections.sort(comparedItems, tradeComparator.getSecondToFirst());
        }else if(order.equals("first_price_asc")){
            Collections.sort(comparedItems, tradeComparator.getFirstServicePriceAsc());
        }else if(order.equals("second_price_asc")){
            Collections.sort(comparedItems, tradeComparator.getSecondServicePriceAsc());
        }else if(order.equals("first_price_desc")){
            Collections.sort(comparedItems, tradeComparator.getFirstServicePriceDesc());
        }else if(order.equals("second_price_desc")){
            Collections.sort(comparedItems, tradeComparator.getSecondServicePriceDesc());
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
