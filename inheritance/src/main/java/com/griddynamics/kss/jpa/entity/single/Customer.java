package com.griddynamics.kss.jpa.entity.single;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Customer extends Person {

    protected BigDecimal discount;

    public Customer(int id, String firstName, String lastName, BigDecimal discount) {
        super(id, firstName, lastName);
        this.discount = discount;
    }

    public Customer() {
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
