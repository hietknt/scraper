package com.parser.scraper.service.comparator;

import com.parser.scraper.model.ComparedItem;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class TradeComparator {

    public Comparator getFirstToSecond() {
        return Comparator.comparingDouble(ComparedItem::getFirstToSecondPercent);
    }

    public Comparator getSecondToFirst() {
        return Comparator.comparingDouble(ComparedItem::getSecondToFirstPercent);
    }

    public Comparator getFirstServicePriceAsc() {
        return Comparator.comparingDouble(ComparedItem::getFirstPrice);
    }

    public Comparator getFirstServicePriceDesc() {
        return Comparator.comparingDouble(ComparedItem::getFirstPrice).reversed();
    }

    public Comparator getSecondServicePriceAsc() {
        return Comparator.comparingDouble(ComparedItem::getSecondPrice);
    }

    public Comparator getSecondServicePriceDesc() {
        return Comparator.comparingDouble(ComparedItem::getSecondPrice).reversed();
    }
}
