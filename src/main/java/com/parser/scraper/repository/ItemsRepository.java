package com.parser.scraper.repository;

import com.parser.scraper.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {

    Set<Items> findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqual(int marketId, long gameId, double maxPrice, double minPrice, int serviceMinCount, int serviceMaxCount);

    @Query("select i from Items i where i.marketId = ?1 and i.gameId = ?2 and i.price < ?3 and i.price > ?4 and i.amount >= ?5 and i.amount <= ?6 and i.amount < i.max")
    Set<Items> findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqualAndAmountLessThenEqualMax(int marketId, long gameId, double maxPrice, double minPrice, int serviceMinCount, int serviceMaxCount);
}
