package com.parser.scraper.service.parse.steam.buff163.jsonBlocks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Buff163Data {
    @JsonProperty("items")
    @Getter @Setter private List<Buff163ItemInfo> items;
}
