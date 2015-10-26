package com.griddynamics.kss.jpa.entity.single;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Employee extends Person {

    protected BigDecimal salary;

    public Employee(int id, String firstName, String lastName, BigDecimal salary) {
        super(id, firstName, lastName);
        this.salary = salary;
    }

    public Employee() {
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
