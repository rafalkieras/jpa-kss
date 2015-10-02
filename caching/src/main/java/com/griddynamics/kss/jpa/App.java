package com.griddynamics.kss.jpa;


import com.griddynamics.kss.jpa.entity.Customer;
import com.griddynamics.kss.jpa.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Cache;
import javax.persistence.CacheRetrieveMode;

import java.util.HashMap;
import java.util.Map;

import static com.griddynamics.kss.jpa.helper.Transactions.inTransaction;

public class App
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        inTransaction(em -> {
            em.persist(new Employee(1, "John", "Doe"));
            em.persist(new Customer(1, "John", "Smithson"));
        });

        checkIfEmployeeWasCached();

        checkIfCustomerWasCached();

        loadEmployeeFromDatabaseViaRefresh();

        loadEmployeeFromDatabaseOmittingCache();

        System.exit(0);
    }

    private static void loadEmployeeFromDatabaseOmittingCache() {
        inTransaction(em -> {
            Map<String, Object> props = new HashMap<>();
            props.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
            LOG.info("Retrieving employee from database");
            Employee employee = em.find(Employee.class, 1, props);
        });
    }

    private static void loadEmployeeFromDatabaseViaRefresh() {
        inTransaction(em -> {
            Employee employee = em.find(Employee.class, 1);
            if (em.getEntityManagerFactory().getCache().contains(Employee.class, 1)) {
                LOG.info("Employee was in the cache, refreshing");
                em.refresh(employee);
            }
        });
    }

    private static void checkIfEmployeeWasCached() {
        inTransaction(em -> {
            Cache cache = em.getEntityManagerFactory().getCache();
            boolean contains = cache.contains(Employee.class, 1);
            LOG.info("Was employee in the cache: {}", contains);
        });
    }

    private static void checkIfCustomerWasCached() {
        inTransaction(em -> {
            Cache cache = em.getEntityManagerFactory().getCache();
            boolean contains = cache.contains(Customer.class, 1);
            LOG.info("Was customer in the cache: {}", contains);
        });
    }
}
