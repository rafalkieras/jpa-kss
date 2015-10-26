package com.griddynamics.kss.jpa;


import com.griddynamics.kss.jpa.entity.Employee;
import com.griddynamics.kss.jpa.entity.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.griddynamics.kss.jpa.helper.Transactions.inTransaction;

public class App
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        createData();

        showNPlusOneProblem();

        LOG.info("Fetching the phones");
        preFetchPhones();

        LOG.info("Fetching the phones without duplicated employees");
        preFetchPhonesWithDistinct();

        System.exit(0);
    }

    private static void createData() {
        inTransaction(em -> {
            Employee johnDoe = new Employee(1, "John", "Doe");
            johnDoe.addPhone(new Phone(1, "123-456-789"));
            johnDoe.addPhone(new Phone(2, "111-222-999"));
            em.persist(johnDoe);

            Employee johnSmithson = new Employee(2, "John", "Smithson");
            johnSmithson.addPhone(new Phone(3, "666-777-789"));
            johnSmithson.addPhone(new Phone(4, "800-900-999"));
            em.persist(johnSmithson);
        });
    }

    private static void showNPlusOneProblem() {
        inTransaction(em -> {
            List<Employee> employees = em.createQuery("from Employee e ", Employee.class)
                    .getResultList();
            logEmployees(employees);
        });
    }

    /**
     * This will result in duplicated employees in returned result set
     */
    private static void preFetchPhones() {
        inTransaction(em -> {
            List<Employee> employees = em.createQuery("from Employee e join fetch e.phones", Employee.class)
                    .getResultList();
            logEmployees(employees);
        });
    }

    /**
     * Phones pre fetched, no duplicates
     */
    private static void preFetchPhonesWithDistinct() {
        inTransaction(em -> {
            List<Employee> employees = em.createQuery("select distinct e from Employee e join fetch e.phones", Employee.class)
                    .getResultList();
            logEmployees(employees);
        });
    }

    private static void logEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            LOG.info("{} {} phones:", employee.getFirstName(), employee.getLastName());
            for (Phone phone : employee.getPhones()) {
                LOG.info("{}", phone.getNumber());
            }
        }
    }
}
