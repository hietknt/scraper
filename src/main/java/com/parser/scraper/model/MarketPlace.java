package com.parser.scraper.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Data
@Entity
@Table(schema = "tests", name = "MarketPlace")
public class MarketPlace {
    @Id
    private long id;
    private String name;
    private double commission;
    private String link;
}
