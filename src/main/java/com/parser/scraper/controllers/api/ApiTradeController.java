package com.parser.scraper.controllers.api;

import com.parser.scraper.model.ComparedItem;
import com.parser.scraper.model.Item;
import com.parser.scraper.model.MarketPlace;
import com.parser.scraper.repository.ComparedItemRepository;
import com.parser.scraper.repository.ItemRepository;
import com.parser.scraper.repository.MarketPlaceRepository;
import com.parser.scraper.service.comparator.TradeComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApiTradeController {
    private final MarketPlaceRepository marketPlaceRepository;
    private final ItemRepository itemRepository;
    private final TradeComparator tradeComparator;
    private final ComparedItemRepository comparedItemRepository;

    @GetMapping("/getAll/{gameId}")
    public List<Item> getAll(@PathVariable long gameId) {
        return itemRepository.findByGameId(gameId);
    }

    @GetMapping("/getByName")
    public List<Item> getByName(@RequestParam String name) {
        return itemRepository.findByNameIgnoreCase(name);
    }

    @GetMapping("/getByMarketId/{gameId}")
    public List<Item> getByMarketId(@PathVariable long gameId,
                                    @RequestParam int marketId) {
        return itemRepository.findByMarketIdAndGameId(marketId, gameId);
    }

    @GetMapping("/getWithPriceLimits/{gameId}")
    public List<Item> getWithPriceLimits(@PathVariable long gameId,
                                         @RequestParam int marketId,
                                         @RequestParam double minPrice,
                                         @RequestParam double maxPrice) {
        return itemRepository.findByMarketIdAndGameIdAndPriceGreaterThanAndPriceLessThan(marketId, gameId, minPrice, maxPrice);
    }

    @GetMapping("/getWithPriceLimitsWithoutOverstock/{gameId}")
    public List<Item> getWithPriceLimitsWithoutOverstock(@PathVariable long gameId,
                                                         @RequestParam int marketId,
                                                         @RequestParam double minPrice,
                                                         @RequestParam double maxPrice) {
        return itemRepository.findByMarketIdAndGameIdAndPriceGreaterThanAndPriceLessThanWithoutOverstock(marketId, gameId, minPrice, maxPrice);
    }

    @GetMapping("/getAllCompared/{gameId}")
    public List<ComparedItem> getAllCompared() {
        return null;
    }

    @GetMapping("getComparedWithPriceLimits/{gameId}")
    public List<ComparedItem> getAllComparedWithPriceLimits() {
        return null;
    }

    @GetMapping("getComparedWithFullParams/{gameId}")
    public List<ComparedItem> getComparedWithFullParams(
            @RequestParam(required = false, defaultValue = "0") double minPrice,
            @RequestParam(required = false, defaultValue = "10000") double maxPrice,
            @RequestParam(required = false, defaultValue = "false") boolean isOverStocked,
            @RequestParam(required = false, defaultValue = "none") String sortOrder,
            @RequestParam(required = false, defaultValue = "0") int firstServiceMinCount,
            @RequestParam(required = false, defaultValue = "10000") int firstServiceMaxCount,
            @RequestParam(required = false, defaultValue = "0") int secondServiceMinCount,
            @RequestParam(required = false, defaultValue = "10000") int secondServiceMaxCount,
            @RequestParam(required = false, defaultValue = "-1000") double firstToSecondMinPerCent,
            @RequestParam(required = false, defaultValue = "1000") double firstToSecondMaxPerCent,
            @RequestParam(required = false, defaultValue = "-1000") double secondToFirstMinPerCent,
            @RequestParam(required = false, defaultValue = "1000") double secondToFirstMaxPerCent,
            @RequestParam(required = false, defaultValue = "") String itemName,
            @RequestParam(required = false, defaultValue = "25") int itemSize,
            @RequestParam(required = false, defaultValue = "24:00:00") String timeFromLastUpdate,
            @RequestParam String firstMarket,
            @RequestParam String secondMarket,
            @PathVariable long gameId) {

        if (firstServiceMinCount < 0) {
            firstServiceMinCount = 0;
        }
        if (firstServiceMaxCount < 0) {
            firstServiceMaxCount = 10000;
        }
        if (secondServiceMinCount < 0) {
            secondServiceMinCount = 0;
        }
        if (secondServiceMaxCount < 0) {
            secondServiceMaxCount = 10000;
        }

        MarketPlace firstMarketPlace;
        MarketPlace secondMarketPlace;
        List<MarketPlace> marketPlaces = marketPlaceRepository.findAll();
        if (marketPlaces.stream().filter(object -> object.getName().equals(firstMarket)).findFirst().isPresent() &&
                marketPlaces.stream().filter(object -> object.getName().equals(secondMarket)).findFirst().isPresent()) {
            firstMarketPlace = marketPlaces.stream().filter(object -> object.getName().equals(firstMarket)).findFirst().get();
            secondMarketPlace = marketPlaces.stream().filter(object -> object.getName().equals(secondMarket)).findFirst().get();
        } else {
            return null;
        }

        List<ComparedItem> items;

        if (isOverStocked == true) {
            if (sortOrder.equals("second_first")) {
                items = comparedItemRepository.getToFirstOverstock(gameId, (int) firstMarketPlace.getId(), (int) secondMarketPlace.getId(), minPrice, maxPrice, firstServiceMinCount, firstServiceMaxCount, secondServiceMinCount, secondServiceMaxCount, firstToSecondMinPerCent, firstToSecondMaxPerCent, secondToFirstMinPerCent, secondToFirstMaxPerCent, timeFromLastUpdate);
            } else {
                items = comparedItemRepository.getToSecondOverstock(gameId, (int) firstMarketPlace.getId(), (int) secondMarketPlace.getId(), minPrice, maxPrice, firstServiceMinCount, firstServiceMaxCount, secondServiceMinCount, secondServiceMaxCount, firstToSecondMinPerCent, firstToSecondMaxPerCent, secondToFirstMinPerCent, secondToFirstMaxPerCent, timeFromLastUpdate);
            }
        } else {
            items = comparedItemRepository.getFull(gameId, (int) firstMarketPlace.getId(), (int) secondMarketPlace.getId(), minPrice, maxPrice, firstServiceMinCount, firstServiceMaxCount, secondServiceMinCount, secondServiceMaxCount, firstToSecondMinPerCent, firstToSecondMaxPerCent, secondToFirstMinPerCent, secondToFirstMaxPerCent, timeFromLastUpdate);
        }

        List<ComparedItem> comparedItems = new LinkedList<>();
        items.stream().forEach(item -> {
            if (Stream.of(itemName.toLowerCase().split(" ")).allMatch(item.getName().toLowerCase()::contains)) {
                comparedItems.add(item);
            }
        });

        if (sortOrder.equals("first_second")) {
            Collections.sort(comparedItems, tradeComparator.getFirstToSecond());
        } else if (sortOrder.equals("second_first")) {
            Collections.sort(comparedItems, tradeComparator.getSecondToFirst());
        } else if (sortOrder.equals("first_price_asc")) {
            Collections.sort(comparedItems, tradeComparator.getFirstServicePriceAsc());
        } else if (sortOrder.equals("second_price_asc")) {
            Collections.sort(comparedItems, tradeComparator.getSecondServicePriceAsc());
        } else if (sortOrder.equals("first_price_desc")) {
            Collections.sort(comparedItems, tradeComparator.getFirstServicePriceDesc());
        } else if (sortOrder.equals("second_price_desc")) {
            Collections.sort(comparedItems, tradeComparator.getSecondServicePriceDesc());
        }

        return comparedItems.size() >= itemSize ? comparedItems.subList(0, itemSize) : comparedItems.subList(0, comparedItems.size());
    }

    @GetMapping("/getMarkets")
    public List<MarketPlace> getMarkets(){
        return marketPlaceRepository.findAll();
    }
}
