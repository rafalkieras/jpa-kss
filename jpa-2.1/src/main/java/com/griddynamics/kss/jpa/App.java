package com.griddynamics.kss.jpa;


import com.griddynamics.kss.jpa.entity.Employee;
import com.griddynamics.kss.jpa.helper.Transactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class App
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        Transactions.inTransaction(em -> {
            StoredProcedureQuery currentDateProcedure = em.createStoredProcedureQuery("floor");

            currentDateProcedure.registerStoredProcedureParameter(0, Double.class, ParameterMode.IN);

            currentDateProcedure.setParameter(0, 6.454);

            System.out.println(currentDateProcedure.getSingleResult());

        });

        Transactions.inTransaction(em -> {
            List<Employee> employees = em.createQuery("from Employee", Employee.class).getResultList();
            LOG.info("Employees size: {}", employees.size());
            LOG.info("Employee hiring date: {}", employees.get(0).getHiringDate());
        });

        System.exit(0);

    }
}
