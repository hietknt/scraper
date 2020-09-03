package com.parser.scraper.model;

import lombok.Getter;
import lombok.Setter;

public class ComparedItem {
    @Getter @Setter private Items firstService;
    @Getter @Setter private Items secondService;
    @Getter @Setter private double firstToSecondProfit;
    @Getter @Setter private double secondToFirstProfit;

    public ComparedItem(Items firstService, Items secondService) {
        this.firstService = firstService;
        this.secondService = secondService;
        this.firstToSecondProfit = (secondService.getPrice()/firstService.getPrice() - 1.0) * 100.0;
        this.secondToFirstProfit = (firstService.getPrice()/secondService.getPrice() - 1.0) * 100.0;
    }

    public double getFirstServicePrice(){
        return this.firstService.getPrice();
    }

    public double getSecondServicePrice(){
        return this.secondService.getPrice();
    }
}
