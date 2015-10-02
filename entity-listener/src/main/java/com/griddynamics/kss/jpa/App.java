package com.griddynamics.kss.jpa;


import com.griddynamics.kss.jpa.entity.Customer;
import com.griddynamics.kss.jpa.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static com.griddynamics.kss.jpa.helper.Transactions.inTransaction;

public class App
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        inTransaction(em -> em.persist(new Employee(1, "John", "Doe")));

        inTransaction(em -> em.persist(new Customer(1, "John", "Smithson")));

        System.exit(0);
    }
}
