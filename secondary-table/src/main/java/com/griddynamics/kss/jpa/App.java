package com.griddynamics.kss.jpa;


import com.griddynamics.kss.jpa.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.griddynamics.kss.jpa.helper.Transactions.inTransaction;

public class App
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        inTransaction(em -> {
            Employee johnDoe = new Employee(1, "John", "Doe", "London", "Liverpool St");
            em.persist(johnDoe);
        });

        inTransaction(em -> {
            Employee employee = em.find(Employee.class, 1);
        });

        System.exit(0);
    }
}
