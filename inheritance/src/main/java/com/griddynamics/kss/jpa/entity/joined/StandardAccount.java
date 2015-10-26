package com.griddynamics.kss.jpa.entity.joined;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class StandardAccount extends Account {

    private String withdrawalAddress;

    public StandardAccount(int id, BigDecimal balance, String withdrawalAddress) {
        super(id, balance);
        this.withdrawalAddress = withdrawalAddress;
    }

    public StandardAccount() {
    }

    public String getWithdrawalAddress() {
        return withdrawalAddress;
    }

    public void setWithdrawalAddress(String withdrawalAddress) {
        this.withdrawalAddress = withdrawalAddress;
    }
}
