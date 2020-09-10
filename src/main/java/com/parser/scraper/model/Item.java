package com.parser.scraper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(schema = "tests", name = "Item")
@IdClass(ItemPK.class)
public class Item {

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

    public Item(String name, double price, int amount, int max, int marketId, long gameId) {
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
        Item item = (Item) object;
        return name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void incrementAmount(){
        this.amount += 1;
    }
}
