package com.parser.scraper.service.updater;

import com.parser.scraper.model.Item;
import com.parser.scraper.repository.ItemRepository;
import com.parser.scraper.service.parse.steam.tradeit.ParseTradeit;
import com.parser.scraper.service.parse.steam.tradeit.jsonBlocks.ItemInfo;
import com.parser.scraper.service.parse.steam.tradeit.jsonBlocks.ItemTradeit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateTradeit implements UpdateItems {

    private final ParseTradeit tradeit;
    private final ItemRepository repository;

    @Override
    public void updateItemsInfo(long gameId) {
        long startTime, time;
        List<Item> itemList = new LinkedList<>();
        for (; ; ) {
            itemList.clear();
            try {
                log.info("TradeIt id " + gameId + " updater start at: " + new Date());
                startTime = System.currentTimeMillis();
                Iterator<ItemTradeit> iterator = tradeit.parse(String.valueOf(gameId)).iterator();
                while (iterator.hasNext()) {
                    Map<String, ItemInfo> item = iterator.next().getItems();
                    if (item.size() != 0) {
                        for (Map.Entry<String, ItemInfo> map : item.entrySet()) {
                            ItemInfo info = map.getValue();

                            if(itemList.stream().filter(object -> object.getName().equals(map.getKey())).findFirst().isPresent()){
                                itemList.stream().filter(object -> object.getName().equals(map.getKey())).findFirst().get().incrementAmount();
                            }else {
                                itemList.add(new Item(map.getKey(), info.getPrice(), info.getAmount(), info.getMaxAmount(),
                                        info.getMarketId(), gameId));
                            }
                        }
                    }
                }
                if(itemList != null) {
                    repository.saveAll(itemList);
                }

                time = System.currentTimeMillis() - startTime;
                log.info("TradeIt id " + gameId + " updater end. Execution lasted: " + time + " ms");
            } catch (Exception ex) {
                log.error("Thread exception: ", ex);
            }
        }
    }
}
