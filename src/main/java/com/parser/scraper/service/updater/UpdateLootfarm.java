package com.parser.scraper.service.updater;

import com.parser.scraper.model.Items;
import com.parser.scraper.repository.ItemsRepository;
import com.parser.scraper.service.parse.steam.lootfarm.ItemLootfarm;
import com.parser.scraper.service.parse.steam.lootfarm.ParseLootfarm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateLootfarm implements UpdateItems {

    private final ParseLootfarm lootfarm;
    private final ItemsRepository repository;

    @Override
    public void updateItemsInfo(long gameId) {
        long startTime, time;
        for (; ; ) {
            try {
                log.info("LootFarm id " + gameId + " updater start at: " + new Date());
                startTime = System.currentTimeMillis();
                Iterator<ItemLootfarm> iterator = lootfarm.parse(String.valueOf(gameId)).iterator();
                while (iterator.hasNext()) {
                    ItemLootfarm item = iterator.next();
                    repository.save(new Items(item.getName(), item.getPrice(), item.getHave(), item.getMax(),
                            item.getMarketId(), gameId));
                }
                time = System.currentTimeMillis() - startTime;
                log.info("LootFarm id " + gameId + " updater end. Execution lasted: " + time + " ms");
            } catch (Exception ex) {
                log.error("Thread exception: ", ex);
            }
        }
    }
}
