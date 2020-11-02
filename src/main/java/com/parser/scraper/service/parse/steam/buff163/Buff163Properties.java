package com.parser.scraper.service.parse.steam.buff163;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties
@PropertySource("classpath:properties/buff163.properties")
public class Buff163Properties {
    String csrfToken;
    String localeSupported;
    String session;
}
