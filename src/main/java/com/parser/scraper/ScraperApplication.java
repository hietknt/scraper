package com.parser.scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ScraperApplication {
    public static void main(String[] args) { SpringApplication.run(ScraperApplication.class, args); }
}
