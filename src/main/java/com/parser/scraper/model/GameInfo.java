package com.parser.scraper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(schema = "tests", name = "GameInfo")
public class GameInfo {
    @Id
    long gameId;
    String gameName;

    public GameInfo(long gameId, String gameName) {
        this.gameId = gameId;
        this.gameName = gameName;
    }
}
