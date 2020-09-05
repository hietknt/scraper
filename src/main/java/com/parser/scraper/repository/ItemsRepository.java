package com.parser.scraper.repository;

import com.parser.scraper.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {

    Set<Items> findByMarketIdAndGameIdAndPriceLessThanAndPriceGreaterThanAndAmountGreaterThanEqualAndAmountLessThanEqual(int marketId, long gameId, double maxPrice, double minPrice, int serviceMinCount, int serviceMaxCount);
}
