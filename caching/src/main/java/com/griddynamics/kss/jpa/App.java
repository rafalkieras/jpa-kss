package com.griddynamics.kss.jpa;


import com.griddynamics.kss.jpa.entity.Customer;
import com.griddynamics.kss.jpa.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Cache;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;

import java.util.HashMap;
import java.util.List;
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

        inTransaction(em -> em.getEntityManagerFactory().getCache().evictAll());

        inTransaction(em -> {
            boolean contains = em.getEntityManagerFactory().getCache().contains(Employee.class, 1);
            LOG.info("Contains: {}", contains);
        });

//        checkIfEmployeeWasCached();
//
//        checkIfCustomerWasCached();
//
//        loadEmployeeFromDatabaseViaRefresh();
//
//        loadEmployeeFromDatabaseOmittingCache();

        inTransaction(em -> {
            List<Employee> employees = em.createQuery("from Employee e", Employee.class)
                    .setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH)
                    .getResultList();

            logEmployees(employees);
        });

        inTransaction(em -> {
            boolean contains = em.getEntityManagerFactory().getCache().contains(Employee.class, 1);
            LOG.info("Contains: {}", contains);
        });

        System.exit(0);
    }

    private static void logEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            LOG.info("{} {}", employee.getFirstName(), employee.getId());
        }

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
