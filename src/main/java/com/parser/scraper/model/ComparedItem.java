package com.parser.scraper.model;

import lombok.Getter;
import lombok.Setter;

public class ComparedItem {
    @Getter @Setter private Item firstService;
    @Getter @Setter private Item secondService;
    @Getter @Setter private double firstToSecondProfit;
    @Getter @Setter private double secondToFirstProfit;

    public ComparedItem(Item firstService, Item secondService) {
        this.firstService = firstService;
        this.secondService = secondService;
        this.firstToSecondProfit = Math.round((secondService.getPrice()/firstService.getPrice() - 1.0) * 10000.0)/ 100.0;
        this.secondToFirstProfit = Math.round((firstService.getPrice()/secondService.getPrice() - 1.0) * 10000.0)/ 100.0;
    }
}
