package com.parser.scraper.service.parse.steam.tradeit.jsonBlocks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeitGameBlock {
    @JsonProperty("items")
    private TradeitItemBlock itemsBlock;
}
