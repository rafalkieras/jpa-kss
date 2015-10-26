package com.griddynamics.kss.jpa.entity.joined;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class PremiumAccount extends Account {

    protected String cardNumber;

    public PremiumAccount(int id, BigDecimal balance, String cardNumber) {
        super(id, balance);
        this.cardNumber = cardNumber;
    }

    public PremiumAccount() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
