package com.parser.scraper.service;

import com.parser.scraper.service.updater.UpdateLootfarm;
import com.parser.scraper.service.updater.UpdateTradeit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Slf4j
@Async
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AsyncParser implements Runnable {
    @Qualifier("taskExecutor")
    private final ThreadPoolTaskExecutor executor;

    private final UpdateLootfarm updateLootfarm;
    private final UpdateTradeit updateTradeit;

    @Override
    public void run() {

        //LootFarm
        executor.execute(() -> updateLootfarm.updateItemsInfo(730));
        executor.execute(() -> updateLootfarm.updateItemsInfo(570));
        executor.execute(() -> updateLootfarm.updateItemsInfo(440));
        executor.execute(() -> updateLootfarm.updateItemsInfo(252490));
        executor.execute(() -> updateLootfarm.updateItemsInfo(433850));

        //TradeIt
        executor.execute(() -> updateTradeit.updateItemsInfo(730));
        executor.execute(() -> updateTradeit.updateItemsInfo(570));
        executor.execute(() -> updateTradeit.updateItemsInfo(440));
        executor.execute(() -> updateTradeit.updateItemsInfo(252490));
        executor.execute(() -> updateTradeit.updateItemsInfo(433850));
    }

    //@PostConstruct
    public void startBackgroundParse() {
        Thread backgroundParse = new Thread(this);
        backgroundParse.start();
    }
}
