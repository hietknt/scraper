package com.parser.scraper.repository;

import com.parser.scraper.model.ComparedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComparedItemRepository extends JpaRepository<ComparedItem, Long> {

    @Query(value = "SELECT \n" +
            "t1.name, t1.amount AS first_amount, t1.max AS first_max, t1.price AS first_price,\n" +
            "t2.amount AS second_amount, t2.max AS second_max, t2.price AS second_price,\n" +
            "CAST(to_char((t1.price/t2.price-1)*100, 'FM999999990.00') AS float8) AS first_to_second_percent,\n" +
            "CAST(to_char((t2.price/t1.price-1)*100, 'FM999999990.00') AS float8) AS second_to_first_percent\n" +
            "FROM tests.item t1 \n" +
            "INNER JOIN tests.item t2\n" +
            "ON t1.name = t2.name\n" +
            "WHERE t1.game_id = ?1 AND t2.game_id = ?1\n" +
            "AND t1.market_id = ?2 AND t2.market_id = ?3\n" +
            "AND t1.price > ?4 AND t1.price < ?5\n" +
            "AND t2.price > ?4 AND t2.price < ?5\n" +
            "AND t1.amount >= ?6 AND t1.amount < ?7\n" +
            "AND t2.amount >= ?8 AND t2.amount < ?9\n" +
            "AND (t1.price/t2.price-1)*100 > ?10 AND (t1.price/t2.price-1)*100 < ?11\n" +
            "AND (t2.price/t1.price-1)*100 > ?12 AND (t2.price/t1.price-1)*100 < ?13\n" +
            //"AND (NOW() - t1.update_date_time) < cast(?14 as time)\n" +
            //"AND (NOW() - t2.update_date_time) < cast(?14 as time)\n" +
            "ORDER BY name;",
            nativeQuery = true)
    List<ComparedItem> getFull(long gameId, int firstMarketId, int secondMarketId,
                               double minPrice, double maxPrice,
                               int firstServiceMinCount, int firstServiceMaxCount,
                               int secondServiceMinCount, int secondServiceMaxCount,
                               double firstToSecondMinPerCent, double firstToSecondMaxPerCent,
                               double secondToFirstMinPerCent, double secondToFirstMaxPerCent,
                               String timeFromLastUpdate);

    @Query(value = "SELECT \n" +
            "t1.name, t1.amount AS first_amount, t1.max AS first_max, t1.price AS first_price,\n" +
            "t2.amount AS second_amount, t2.max AS second_max, t2.price AS second_price,\n" +
            "CAST(to_char((t1.price/t2.price-1)*100, 'FM999999990.00') AS float8) AS first_to_second_percent,\n" +
            "CAST(to_char((t2.price/t1.price-1)*100, 'FM999999990.00') AS float8) AS second_to_first_percent\n" +
            "FROM tests.item t1 \n" +
            "INNER JOIN tests.item t2\n" +
            "ON t1.name = t2.name\n" +
            "WHERE t1.game_id = ?1 AND t2.game_id = ?1\n" +
            "AND t1.market_id = ?2 AND t2.market_id = ?3\n" +
            "AND t1.price > ?4 AND t1.price < ?5\n" +
            "AND t2.price > ?4 AND t2.price < ?5\n" +
            "AND t1.amount >= ?6 AND t1.amount < ?7\n" +
            "AND t2.amount >= ?8 AND t2.amount < ?9\n" +
            "AND (t1.price/t2.price-1)*100 > ?10 AND (t1.price/t2.price-1)*100 < ?11\n" +
            "AND (t2.price/t1.price-1)*100 > ?12 AND (t2.price/t1.price-1)*100 < ?13\n" +
            "AND t1.amount < t1.max\n" +
            //"AND (NOW() - t1.update_date_time) < cast(?14 as time)\n" +
            //"AND (NOW() - t2.update_date_time) < cast(?14 as time)\n" +
            "ORDER BY name;",
            nativeQuery = true)
    List<ComparedItem> getToFirstOverstock(long gameId, int firstMarketId, int secondMarketId,
                                           double minPrice, double maxPrice,
                                           int firstServiceMinCount, int firstServiceMaxCount,
                                           int secondServiceMinCount, int secondServiceMaxCount,
                                           double firstToSecondMinPerCent, double firstToSecondMaxPerCent,
                                           double secondToFirstMinPerCent, double secondToFirstMaxPerCent,
                                           String timeFromLastUpdate);

    @Query(value = "SELECT \n" +
            "t1.name, t1.amount AS first_amount, t1.max AS first_max, t1.price AS first_price,\n" +
            "t2.amount AS second_amount, t2.max AS second_max, t2.price AS second_price,\n" +
            "CAST(to_char((t1.price/t2.price-1)*100, 'FM999999990.00') AS float8) AS first_to_second_percent,\n" +
            "CAST(to_char((t2.price/t1.price-1)*100, 'FM999999990.00') AS float8) AS second_to_first_percent\n" +
            "FROM tests.item t1 \n" +
            "INNER JOIN tests.item t2\n" +
            "ON t1.name = t2.name\n" +
            "WHERE t1.game_id = ?1 AND t2.game_id = ?1\n" +
            "AND t1.market_id = ?2 AND t2.market_id = ?3\n" +
            "AND t1.price > ?4 AND t1.price < ?5\n" +
            "AND t2.price > ?4 AND t2.price < ?5\n" +
            "AND t1.amount >= ?6 AND t1.amount < ?7\n" +
            "AND t2.amount >= ?8 AND t2.amount < ?9\n" +
            "AND (t1.price/t2.price-1)*100 > ?10 AND (t1.price/t2.price-1)*100 < ?11\n" +
            "AND (t2.price/t1.price-1)*100 > ?12 AND (t2.price/t1.price-1)*100 < ?13\n" +
            "AND t2.amount < t2.max\n" +
            //"AND (NOW() - t1.update_date_time) < cast(?14 as time)\n" +
            //"AND (NOW() - t2.update_date_time) < cast(?14 as time)\n" +
            "ORDER BY name;",
            nativeQuery = true)
    List<ComparedItem> getToSecondOverstock(long gameId, int firstMarketId, int secondMarketId,
                                            double minPrice, double maxPrice,
                                            int firstServiceMinCount, int firstServiceMaxCount,
                                            int secondServiceMinCount, int secondServiceMaxCount,
                                            double firstToSecondMinPerCent, double firstToSecondMaxPerCent,
                                            double secondToFirstMinPerCent, double secondToFirstMaxPerCent,
                                            String timeFromLastUpdate);
}
