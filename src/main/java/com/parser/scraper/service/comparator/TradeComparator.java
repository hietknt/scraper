package com.parser.scraper.service.comparator;

import com.parser.scraper.model.ComparedItem;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class TradeComparator {

    public Comparator getFirstToSecond(){ return Comparator.comparingDouble(ComparedItem::getSecondToFirstProfit); }

    public Comparator getSecondToFirst(){ return Comparator.comparingDouble(ComparedItem::getFirstToSecondProfit); }

    public Comparator getFirstServicePriceAsc(){ return Comparator.comparingDouble((ComparedItem item) -> item.getFirstService().getPrice()); }
    public Comparator getFirstServicePriceDesc(){ return Comparator.comparingDouble((ComparedItem item) -> item.getFirstService().getPrice()).reversed(); }

    public Comparator getSecondServicePriceAsc(){ return Comparator.comparingDouble((ComparedItem item) -> item.getSecondService().getPrice()); }
    public Comparator getSecondServicePriceDesc(){ return Comparator.comparingDouble((ComparedItem item) -> item.getSecondService().getPrice()).reversed(); }



}
