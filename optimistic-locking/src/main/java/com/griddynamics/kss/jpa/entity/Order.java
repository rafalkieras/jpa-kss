package com.griddynamics.kss.jpa.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "APP_ORDER")
public class Order {

    @Id
    private int id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ORDER_ITEM")
    private Set<Item> items = new HashSet<>();

    public Order(int id) {
        this.id = id;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
