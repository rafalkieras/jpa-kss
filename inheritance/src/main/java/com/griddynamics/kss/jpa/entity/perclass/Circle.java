package com.griddynamics.kss.jpa.entity.perclass;

import javax.persistence.Entity;

@Entity
public class Circle extends Shape {

    protected double radius;

    public Circle(int id, double y, double x, double radius) {
        super(id, y, x);
        this.radius = radius;
    }

    public Circle() {
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
