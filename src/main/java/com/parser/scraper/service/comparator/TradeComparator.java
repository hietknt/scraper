package com.parser.scraper.service.comparator;

import com.parser.scraper.model.ComparedItem;
import com.parser.scraper.model.TempComparedItem;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class TradeComparator {

    public Comparator getFirstToSecond(){ return Comparator.comparingDouble(TempComparedItem::getFirstToSecondPercent); }

    public Comparator getSecondToFirst(){ return Comparator.comparingDouble(TempComparedItem::getSecondToFirstPercent); }

    public Comparator getFirstServicePriceAsc(){ return Comparator.comparingDouble(TempComparedItem::getFirstPrice); }
    public Comparator getFirstServicePriceDesc(){ return Comparator.comparingDouble(TempComparedItem::getFirstPrice).reversed(); }

    public Comparator getSecondServicePriceAsc(){ return Comparator.comparingDouble(TempComparedItem::getSecondPrice); }
    public Comparator getSecondServicePriceDesc(){ return Comparator.comparingDouble(TempComparedItem::getSecondPrice).reversed(); }



}
