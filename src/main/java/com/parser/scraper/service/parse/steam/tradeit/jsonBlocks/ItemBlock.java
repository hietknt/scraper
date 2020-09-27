package com.parser.scraper.service.parse.steam.tradeit.jsonBlocks;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemBlock {
    @JsonProperty("Items")
    @Getter
    private Map<String, ItemInfo> items = new HashMap<>();

    @JsonAnySetter
    public void setItems(String key, ItemInfo value) {
        String newKey = key.split("_")[1]
                + (value.getQuality().equals("Default") ? "" : (" (" + value.getQuality() + ")"));

        this.items.put(newKey, value);
    }
}
