package com.parser.scraper.repository;

import com.parser.scraper.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqual(int marketId, long gameId, double maxPrice, double minPrice, int serviceMinCount, int serviceMaxCount);

    @Query("select i from Item i where i.marketId = ?1 and i.gameId = ?2 and i.price < ?3 and i.price > ?4 and i.amount >= ?5 and i.amount <= ?6 and i.amount < i.max")
    List<Item> findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqualAndAmountLessThenEqualMax(int marketId, long gameId, double maxPrice, double minPrice, int serviceMinCount, int serviceMaxCount);

    //API
    List<Item> findByGameId(long gameId);
    List<Item> findByName(String name);
    List<Item> findByMarketIdAndGameId(int marketId, long gameId);
    List<Item> findByMarketIdAndGameIdAndPriceGreaterThanAndPriceLessThan(int marketId, long gameId, double minPrice, double maxPrice);
    @Query("select i from Item i where i.marketId = ?1 and i.gameId = ?2 and i.price > ?3 and i.price < ?4 and i.amount < i.max")
    List<Item> findByMarketIdAndGameIdAndPriceGreaterThanAndPriceLessThanWithoutOverstock(int marketId, long gameId, double minPrice, double maxPrice);
}