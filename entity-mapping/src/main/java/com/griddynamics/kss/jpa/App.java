package com.griddynamics.kss.jpa;


import com.griddynamics.kss.jpa.entity.Customer;
import com.griddynamics.kss.jpa.entity.Employee;
import com.griddynamics.kss.jpa.helper.Transactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        Transactions.inTransaction(em -> em.persist(new Customer(1, "John", "Doe")));

        Transactions.inTransaction(em -> {
            Customer johnDoe = em.createQuery("from Customer", Customer.class)
                    .getSingleResult();

            LOG.info("John Doe is in the database with id {}", johnDoe.getId());
        });

        Employee employee = new Employee(1, "John", "Smithson");
        Transactions.inTransaction(em -> em.persist(employee));

        Transactions.inTransaction(em -> {
            Employee johnSmithson = em.createQuery("from Employee", Employee.class)
                    .getSingleResult();

            LOG.info("John Smithson is in the database with id {}", johnSmithson.getId());
        });

        Transactions.inTransaction(em -> LOG.info("DB contains {}", em.contains(employee)));

        System.exit(0);
    }
}
