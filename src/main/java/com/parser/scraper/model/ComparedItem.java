package com.parser.scraper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class ComparedItem {
    @Id
    private String name;
    private int firstAmount;
    private int firstMax;
    private double firstPrice;

    private int secondAmount;
    private int secondMax;
    private double secondPrice;

    private double firstToSecondPercent;
    private double secondToFirstPercent;
}
