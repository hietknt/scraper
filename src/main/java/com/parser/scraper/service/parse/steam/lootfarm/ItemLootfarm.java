package com.parser.scraper.service.parse.steam.lootfarm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemLootfarm {
    @Getter @Setter private String name;
    @Getter private double price;
    @Getter @Setter private int have;
    @Getter @Setter private int max;

    @Getter private int marketId = 1;

    public void setPrice(int price) {
        this.price = price / 100.0;
    }
}
