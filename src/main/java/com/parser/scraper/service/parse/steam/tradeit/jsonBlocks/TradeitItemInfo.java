package com.parser.scraper.service.parse.steam.tradeit.jsonBlocks;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeitItemInfo {
    private static Map<String, String> qualityMap = Stream.of(new String[][]{
            {"FN", "Factory New"},
            {"MW", "Minimal Wear"},
            {"FT", "Field-Tested"},
            {"WW", "Well-Worn"},
            {"BS", "Battle-Scarred"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    @JsonProperty("e")
    @Getter private String quality = "Default";

    @JsonAlias({"p", "cp"})
    @Getter private double price;

    @JsonProperty("x")
    @Getter @Setter private int maxAmount;

    @Getter int marketId = 2;
    @Getter int amount = 1;

    public void setQuality(String quality) {
        if (this.qualityMap.get(quality) != null) {
            this.quality = this.qualityMap.get(quality);
        }
    }

    public void setPrice(int price) {
        this.price = price / 100.0;
    }
}
