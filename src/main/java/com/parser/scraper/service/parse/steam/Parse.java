package com.parser.scraper.service.parse.steam;

import java.util.Collection;

public interface Parse {
    Collection<?> parse(String id);
}