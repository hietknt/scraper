package com.parser.scraper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(schema = "tests", name = "Items")
@IdClass(ItemsPK.class)
public class Items {

    @Id
    @AttributeOverrides({
            @AttributeOverride(name = "name",
                    column = @Column(name = "name")),
            @AttributeOverride(name = "marketId",
                    column = @Column(name = "market_id"))
    })
    private String name;
    private int marketId;
    private double price;
    private int amount;
    private int max;
    private long gameId;

    public Items(String name, double price, int amount, int max, int marketId, long gameId) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.max = max;
        this.marketId = marketId;
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Items items = (Items) object;
        return name.equals(items.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
