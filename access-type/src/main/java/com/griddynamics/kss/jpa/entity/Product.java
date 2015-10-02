package com.griddynamics.kss.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Product {

    private int id;

    private String code;

    private BigDecimal price;

    private BigDecimal discount;

    public Product(int id, String code, BigDecimal price, BigDecimal discount) {
        this.id = id;
        this.code = code;
        this.price = price;
        this.discount = discount;
    }

    protected Product() {
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price.subtract(discount);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
