package com.griddynamics.kss.jpa.entity.perclass;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Shape {

    @Id
    protected int id;

    protected double x;

    protected double y;

    public Shape(int id, double y, double x) {
        this.id = id;
        this.y = y;
        this.x = x;
    }

    public Shape() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
