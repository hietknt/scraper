package com.parser.scraper.controllers.api;

import com.parser.scraper.model.ComparedItem;
import com.parser.scraper.model.Item;
import com.parser.scraper.model.MarketPlace;
import com.parser.scraper.repository.ItemRepository;
import com.parser.scraper.repository.MarketPlaceRepository;
import com.parser.scraper.service.comparator.TradeComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApiTradeController {
    private final MarketPlaceRepository marketPlaceRepository;
    private final ItemRepository itemRepository;
    private final TradeComparator tradeComparator;

    @GetMapping("/getAll/{gameId}")
    public List<Item> getAll(@PathVariable long gameId){
        return itemRepository.findByGameId(gameId);
    }

    @GetMapping("/getByName")
    public List<Item> getByName(@RequestParam String name){
        return itemRepository.findByName(name);
    }

    @GetMapping("/getByMarketId/{gameId}")
    public List<Item> getByMarketId(@PathVariable long gameId,
                                    @RequestParam int marketId){
        return itemRepository.findByMarketIdAndGameId(marketId, gameId);
    }

    @GetMapping("/getWithPriceLimits/{gameId}")
    public List<Item> getWithPriceLimits(@PathVariable long gameId,
                                         @RequestParam int marketId,
                                         @RequestParam double minPrice,
                                         @RequestParam double maxPrice){
        return itemRepository.findByMarketIdAndGameIdAndPriceGreaterThanAndPriceLessThan(marketId, gameId, minPrice, maxPrice);
    }

    @GetMapping("/getWithPriceLimitsWithoutOverstock/{gameId}")
    public List<Item> getWithPriceLimitsWithoutOverstock(@PathVariable long gameId,
                                                         @RequestParam int marketId,
                                                         @RequestParam double minPrice,
                                                         @RequestParam double maxPrice){
        return itemRepository.findByMarketIdAndGameIdAndPriceGreaterThanAndPriceLessThanWithoutOverstock(marketId, gameId, minPrice, maxPrice);
    }

    @GetMapping("/getAllCompared/{gameId}")
    public List<ComparedItem> getAllCompared(@PathVariable long gameId,
                                             @RequestParam int firstMarketId,
                                             @RequestParam int secondMarketId){
        List<Item> firstMarketSet = itemRepository.findByMarketIdAndGameId(firstMarketId, gameId);
        List<Item> secondMarketSet = itemRepository.findByMarketIdAndGameId(secondMarketId, gameId);

        return compare(firstMarketSet, secondMarketSet);
    }

    @GetMapping("getComparedWithPriceLimits/{gameId}")
    public List<ComparedItem> getAllComparedWithPriceLimits(@PathVariable long gameId,
                                                             @RequestParam int firstMarketId,
                                                             @RequestParam int secondMarketId,
                                                             @RequestParam double minPrice,
                                                             @RequestParam double maxPrice){
        List<Item> firstMarketSet = itemRepository.findByMarketIdAndGameIdAndPriceGreaterThanAndPriceLessThan(firstMarketId, gameId, minPrice, maxPrice);
        List<Item> secondMarketSet = itemRepository.findByMarketIdAndGameIdAndPriceGreaterThanAndPriceLessThan(secondMarketId, gameId, minPrice, maxPrice);

        return compare(firstMarketSet, secondMarketSet);
    }

    @GetMapping("getComparedWithFullParams/{gameId}")
    public List<ComparedItem> getComparedWithFullParams(@RequestParam double minPrice,
                                                        @RequestParam double maxPrice,
                                                        @RequestParam boolean isOverStocked,
                                                        @RequestParam String firstMarket,
                                                        @RequestParam String secondMarket,
                                                        @RequestParam String sortOrder,
                                                        @RequestParam int firstServiceMinCount,
                                                        @RequestParam int firstServiceMaxCount,
                                                        @RequestParam int secondServiceMinCount,
                                                        @RequestParam int secondServiceMaxCount,
                                                        @RequestParam double firstToSecondMinPerCent,
                                                        @RequestParam double firstToSecondMaxPerCent,
                                                        @RequestParam double secondToFirstMinPerCent,
                                                        @RequestParam double secondToFirstMaxPerCent,
                                                        @RequestParam String itemName,
                                                        @PathVariable long gameId){

        if (firstServiceMinCount < 0){ firstServiceMinCount = 0; }
        if (firstServiceMaxCount < 0){ firstServiceMaxCount = 10000; }
        if (secondServiceMinCount < 0){ secondServiceMinCount = 0; }
        if (secondServiceMaxCount < 0){ secondServiceMaxCount = 10000; }

        MarketPlace firstMarketPlace;
        MarketPlace secondMarketPlace;
        List<MarketPlace> marketPlaces = marketPlaceRepository.findAll();
        if (    marketPlaces.stream().filter(object -> object.getName().equals(firstMarket)).findFirst().isPresent() &&
                marketPlaces.stream().filter(object -> object.getName().equals(secondMarket)).findFirst().isPresent()){
            firstMarketPlace = marketPlaces.stream().filter(object -> object.getName().equals(firstMarket)).findFirst().get();
            secondMarketPlace = marketPlaces.stream().filter(object -> object.getName().equals(secondMarket)).findFirst().get();
        }else {
            firstMarketPlace = marketPlaces.stream().filter(object -> object.getName().equals("lootfarm")).findFirst().get();
            secondMarketPlace = marketPlaces.stream().filter(object -> object.getName().equals("tradeit")).findFirst().get();
        }

        List<Item> firstMarketSet;
        List<Item> secondMarketSet;
        if (isOverStocked == true){
            if(sortOrder.equals("second_first")){
                firstMarketSet = itemRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqualAndAmountLessThenEqualMax((int) firstMarketPlace.getId(), gameId, maxPrice, minPrice, firstServiceMinCount, firstServiceMaxCount);
                secondMarketSet = itemRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqual((int) secondMarketPlace.getId(), gameId, maxPrice, minPrice, secondServiceMinCount, secondServiceMaxCount);
            }else{
                firstMarketSet = itemRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqual((int) firstMarketPlace.getId(), gameId, maxPrice, minPrice, firstServiceMinCount, firstServiceMaxCount);
                secondMarketSet = itemRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqualAndAmountLessThenEqualMax((int) secondMarketPlace.getId(), gameId, maxPrice, minPrice, secondServiceMinCount, secondServiceMaxCount);
            }
        }else{
            firstMarketSet = itemRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqual((int) firstMarketPlace.getId(), gameId, maxPrice, minPrice, firstServiceMinCount, firstServiceMaxCount);
            secondMarketSet = itemRepository.findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqual((int) secondMarketPlace.getId(), gameId, maxPrice, minPrice, secondServiceMinCount, secondServiceMaxCount);
        }

        List<ComparedItem> comparedItems = new LinkedList<>();
        firstMarketSet.stream().forEach(firstMarketItem -> {
            if (secondMarketSet.contains(firstMarketItem)) {
                if (Stream.of(itemName.toLowerCase().split(" ")).allMatch(firstMarketItem.getName().toLowerCase()::contains)) {
                    Item secondMarketItem = secondMarketSet.stream().filter(firstMarketItem::equals).findAny().get();
                    ComparedItem item = new ComparedItem(firstMarketItem, secondMarketItem);
                    if(firstToSecondMinPerCent <= item.getFirstToSecondProfit() && item.getFirstToSecondProfit() <= firstToSecondMaxPerCent
                            && secondToFirstMinPerCent <= item.getSecondToFirstProfit() && item.getSecondToFirstProfit() <= secondToFirstMaxPerCent) {
                        comparedItems.add(item);
                    }
                }
            }
        });

        if (sortOrder.equals("first_second")){
            Collections.sort(comparedItems, tradeComparator.getFirstToSecond());
        }else if(sortOrder.equals("second_first")){
            Collections.sort(comparedItems, tradeComparator.getSecondToFirst());
        }else if(sortOrder.equals("first_price_asc")){
            Collections.sort(comparedItems, tradeComparator.getFirstServicePriceAsc());
        }else if(sortOrder.equals("second_price_asc")){
            Collections.sort(comparedItems, tradeComparator.getSecondServicePriceAsc());
        }else if(sortOrder.equals("first_price_desc")){
            Collections.sort(comparedItems, tradeComparator.getFirstServicePriceDesc());
        }else if(sortOrder.equals("second_price_desc")){
            Collections.sort(comparedItems, tradeComparator.getSecondServicePriceDesc());
        }

        return comparedItems;
    }

    private List<ComparedItem> compare(List<Item> firstMarketSet, List<Item> secondMarketSet){
        List<ComparedItem> comparedItems = new LinkedList<>();
        firstMarketSet.stream().forEach(firstMarketItem -> {
            if (secondMarketSet.contains(firstMarketItem)) {
                Item secondMarketItem = secondMarketSet.stream().filter(firstMarketItem::equals).findAny().get();

                ComparedItem item = new ComparedItem(firstMarketItem, secondMarketItem);
                comparedItems.add(item);
            }
        });
        return comparedItems;
    }

}
