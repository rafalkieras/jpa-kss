package com.griddynamics.kss.jpa.entity.perclass;

import javax.persistence.Entity;

@Entity
public class Square extends Shape {

    protected double size;

    public Square(int id, double y, double x, double size) {
        super(id, y, x);
        this.size = size;
    }

    public Square() {
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
