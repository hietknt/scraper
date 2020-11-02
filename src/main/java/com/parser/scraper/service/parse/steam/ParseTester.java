package com.parser.scraper.service.parse.steam;

import com.parser.scraper.service.parse.steam.buff163.ParseBuff163;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ParseTester {

    @Autowired
    private ParseBuff163 parseBuff163;

    //@PostConstruct
    public void startBackgroundParse() {
        parseBuff163.parse("0");
    }
}