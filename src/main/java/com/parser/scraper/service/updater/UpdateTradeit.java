package com.parser.scraper.service.updater;

import com.parser.scraper.model.Items;
import com.parser.scraper.repository.ItemsRepository;
import com.parser.scraper.service.parse.steam.tradeit.ParseTradeit;
import com.parser.scraper.service.parse.steam.tradeit.jsonBlocks.ItemInfo;
import com.parser.scraper.service.parse.steam.tradeit.jsonBlocks.ItemTradeit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateTradeit implements UpdateItems {

    private final ParseTradeit tradeit;
    private final ItemsRepository repository;

    @Override
    public void updateItemsInfo(long gameId) {
        long startTime, time;
        for (; ; ) {
            try {
                log.info("TradeIt id " + gameId + " updater start at: " + new Date());
                startTime = System.currentTimeMillis();
                Iterator<ItemTradeit> iterator = tradeit.parse(String.valueOf(gameId)).iterator();
                while (iterator.hasNext()) {
                    Map<String, ItemInfo> item = iterator.next().getItems();
                    if (item.size() != 0) {
                        for (Map.Entry<String, ItemInfo> map : item.entrySet()) {
                            ItemInfo info = map.getValue();
                            repository.save(new Items(map.getKey(), info.getPrice(), info.getAmount(), info.getMaxAmount(),
                                    info.getMarketId(), gameId));
                        }
                    }
                }
                time = System.currentTimeMillis() - startTime;
                log.info("TradeIt id " + gameId + " updater end. Execution lasted: " + time + " ms");
            } catch (Exception ex) {
                log.error("Thread exception: ", ex);
            }
        }
    }
}
