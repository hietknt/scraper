package com.parser.scraper.service.parse.steam.buff163.jsonBlocks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemBuff163 {
    @JsonProperty("data")
    @Setter private Buff163Data data;

    public List<Buff163ItemInfo> getData() {
        return data.getItems();
    }
}