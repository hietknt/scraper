package com.parser.scraper.service.parse.steam;

import com.parser.scraper.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ParseTester {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        /*
        // LootFarm
        ParseLootfarm site1 = context.getBean(ParseLootfarm.class);
        ArrayDeque<ItemLootfarm> loot = site1.parse("730");
        Iterator<ItemLootfarm> iter = loot.iterator();
        while (iter.hasNext()){
            System.out.println(iter.next().getName());
        }
        */


        /*
        // TradeIT
        ParseTradeit site2 = context.getBean(ParseTradeit.class);
        int i2 = 0;
        Map<String, ItemInfo> tradeit = site2.parse("730");
        for (Map.Entry<String, ItemInfo> entry: tradeit.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().getPrice());
            i2++;
        }
        System.out.println(i2);
        */


    }
}