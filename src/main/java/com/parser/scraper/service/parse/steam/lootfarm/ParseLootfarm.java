package com.parser.scraper.service.parse.steam.lootfarm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.scraper.service.parse.steam.Parse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParseLootfarm implements Parse {
    private static Map<String, String> game = Stream.of(new String[][]{
            {"730", "https://loot.farm/fullprice.json"},
            {"570", "https://loot.farm/fullpriceDOTA.json"},
            {"440", "https://loot.farm/fullpriceTF2.json"},
            {"252490", "https://loot.farm/fullpriceRUST.json"},
            {"433850", "https://loot.farm/fullpriceH1Z1.json"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private final ObjectMapper objectMapper;

    @Override
    public ArrayDeque<ItemLootfarm> parse(String id) {
        ArrayDeque<ItemLootfarm> items = null;
        try {
            items = objectMapper.readValue(new URL(game.get(id)), new TypeReference<ArrayDeque<ItemLootfarm>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }
}
