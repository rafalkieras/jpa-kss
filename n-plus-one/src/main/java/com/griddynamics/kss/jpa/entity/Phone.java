package com.griddynamics.kss.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Phone {

    @Id
    private int id;

    private String number;

    public Phone(int id, String number) {
        this.id = id;
        this.number = number;
    }

    protected Phone() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
