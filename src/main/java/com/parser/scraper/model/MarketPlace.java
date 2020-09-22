package com.parser.scraper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(schema = "tests", name = "MarketPlace")
public class MarketPlace {
    @Id
    private long id;
    private String name;
    private double commission;
    private String link;

    public MarketPlace(long id, String name, double commission, String link) {
        this.id = id;
        this.name = name;
        this.commission = commission;
        this.link = link;
    }
}
