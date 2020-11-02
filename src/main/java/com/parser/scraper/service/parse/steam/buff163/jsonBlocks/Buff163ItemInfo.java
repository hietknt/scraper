package com.parser.scraper.service.parse.steam.buff163.jsonBlocks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Buff163ItemInfo {
    @Getter @Setter private String name;

    @JsonProperty("sell_min_price")
    @Getter @Setter private double price;

    @JsonProperty("sell_num")
    @Getter @Setter private int amount;
}
