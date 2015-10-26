package com.griddynamics.kss.jpa;


import com.griddynamics.kss.jpa.entity.joined.Account;
import com.griddynamics.kss.jpa.entity.joined.PremiumAccount;
import com.griddynamics.kss.jpa.entity.joined.StandardAccount;
import com.griddynamics.kss.jpa.entity.perclass.Circle;
import com.griddynamics.kss.jpa.entity.perclass.Shape;
import com.griddynamics.kss.jpa.entity.perclass.Square;
import com.griddynamics.kss.jpa.entity.single.Customer;
import com.griddynamics.kss.jpa.entity.single.Employee;
import com.griddynamics.kss.jpa.entity.single.Person;

import java.math.BigDecimal;
import java.util.List;

import static com.griddynamics.kss.jpa.helper.Transactions.inTransaction;

public class App {

    public static void main(String[] args) {
        singleTable();

        joined();

        perClass();

        System.exit(0);
    }

    private static void perClass() {
        inTransaction(em -> {
            em.persist(new Circle(1, 0.0, 0.0, 4.0));
            em.persist(new Square(2, 110.0, 0.0, 4.0));
        });

        inTransaction(em -> {
            List<Shape> shapes = em.createQuery("from Shape", Shape.class).getResultList();
            System.out.println(shapes);
        });
    }

    private static void joined() {
        inTransaction(em -> {
            em.persist(new PremiumAccount(1, BigDecimal.ZERO, "44331232"));
            em.persist(new StandardAccount(2, BigDecimal.ZERO, "dummy address"));
        });

        inTransaction(em -> {
            List<Account> accounts = em.createQuery("from Account", Account.class).getResultList();
            System.out.println(accounts);
        });
    }

    private static void singleTable() {
        inTransaction(em -> {
            em.persist(new Customer(1, "John", "Doe", BigDecimal.TEN));
            em.persist(new Employee(2, "John", "Doe", BigDecimal.valueOf(2000)));
        });

        inTransaction(em -> {
            List<Person> persons = em.createQuery("from Person", Person.class).getResultList();
            System.out.println(persons);
        });
    }


}
