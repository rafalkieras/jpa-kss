package com.griddynamics.kss.jpa;


import com.griddynamics.kss.jpa.helper.Transactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

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

        System.exit(0);

    }
}
