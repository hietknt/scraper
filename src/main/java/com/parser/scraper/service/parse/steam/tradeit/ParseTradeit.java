package com.parser.scraper.service.parse.steam.tradeit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.scraper.service.parse.steam.Parse;
import com.parser.scraper.service.parse.steam.tradeit.jsonBlocks.ItemTradeit;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayDeque;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParseTradeit implements Parse {
    private static String url = "https://inventory.tradeit.gg/sinv/";

    private final ObjectMapper objectMapper;
    private final int byteJsonSize = 40960000;

    @Override
    public ArrayDeque<ItemTradeit> parse(String id) {

        String json = "";
        try {
            json = Jsoup.connect(url + id)
                    .ignoreContentType(true)
                    .maxBodySize(byteJsonSize)
                    .execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayDeque<ItemTradeit> items = null;
        try {
            items = objectMapper.readValue(json, new TypeReference<ArrayDeque<ItemTradeit>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }
}
