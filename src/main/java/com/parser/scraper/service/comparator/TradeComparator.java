package com.parser.scraper.service.comparator;

import com.parser.scraper.model.ComparedItem;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class TradeComparator {

    public Comparator getFirstToSecond(){
        return Comparator.comparingDouble(ComparedItem::getSecondToFirstProfit);
    }

    public Comparator getSecondToFirst(){
        return Comparator.comparingDouble(ComparedItem::getFirstToSecondProfit);
    }

    public Comparator getFirstServicePrice(){
        return Comparator.comparingDouble(ComparedItem::getFirstServicePrice);
    }

    public Comparator getSecondServicePrice(){
        return Comparator.comparingDouble(ComparedItem::getSecondServicePrice);
    }

}
