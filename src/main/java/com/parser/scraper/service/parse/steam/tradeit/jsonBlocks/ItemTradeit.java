package com.parser.scraper.service.parse.steam.tradeit.jsonBlocks;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemTradeit {
    @JsonAlias({"440", "570", "730", "252490", "433850"})
    private TradeitGameBlock gameBlock;

    public Map<String, TradeitItemInfo> getItems() {
        return gameBlock.getItemsBlock().getItems();
    }
}
