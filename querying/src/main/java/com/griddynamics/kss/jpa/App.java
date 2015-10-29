package com.griddynamics.kss.jpa;


import com.griddynamics.kss.jpa.entity.Customer;
import com.griddynamics.kss.jpa.entity.Employee;
import com.griddynamics.kss.jpa.entity.Employee_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.griddynamics.kss.jpa.helper.Transactions.inTransaction;

public class App
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        inTransaction(em -> {
            em.persist(new Customer(1, "John", "Doe"));
            em.persist(new Customer(2, "Steve", "Doe"));
            em.persist(new Employee(1, "Martin", "Joe"));
            em.persist(new Employee(2, "Martin", "Ward"));
        });

        inTransaction(em -> {
            List<String> employees = em.createQuery("select e.firstName from Employee e", String.class)
                    .getResultList();

            LOG.info("Employees {}", employees);
        });

        inTransaction(em -> {
            List<String> employees = em.createNamedQuery("Employee.getFirstNames", String.class)
                    .getResultList();

            LOG.info("Employees {}", employees);

            List<String> customers = em.createNamedQuery("Customer.getFirstNames", String.class)
                    .getResultList();

            LOG.info("Customers {}", customers);
        });

        criteriaQuery();


        System.exit(0);
    }

    private static void criteriaQuery() {
        inTransaction(em -> {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
            Root<Employee> employee = query.from(Employee.class);

            query.select(employee);
            query.where(builder.equal(employee.get(Employee_.lastName), "Joe"));
            TypedQuery<Employee> typedQuery = em.createQuery(query);
            List<Employee> list = typedQuery.getResultList();

            System.out.println(list);

        });
    }
}
