package com.parser.scraper.config;

import com.parser.scraper.model.GameInfo;
import com.parser.scraper.model.MarketPlace;
import com.parser.scraper.repository.GameInfoRepository;
import com.parser.scraper.repository.MarketPlaceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreloadDatabase {

    @Bean
    public CommandLineRunner initGameDatabase(GameInfoRepository repository){
        return args -> {
            repository.save(new GameInfo(440, "tf2"));
            repository.save(new GameInfo(570, "dota2"));
            repository.save(new GameInfo(730, "csgo"));
            repository.save(new GameInfo(252490, "rust"));
            repository.save(new GameInfo(433850, "h1z1"));
        };
    }

    @Bean
    public CommandLineRunner initMarketPlaceDatabase(MarketPlaceRepository repository){
        return args -> {
            repository.save(new MarketPlace(1, "lootfarm", 0.03, "https://loot.farm/"));
            repository.save(new MarketPlace(2, "tradeit", 0.05, "https://tradeit.gg/"));
            repository.save(new MarketPlace(3, "buff163", 0.025, "https://buff.163.com/"));
        };
    }
}
