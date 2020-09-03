package com.parser.scraper.repository;

import com.parser.scraper.model.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameInfoRepository extends JpaRepository<GameInfo, Long> {
}
