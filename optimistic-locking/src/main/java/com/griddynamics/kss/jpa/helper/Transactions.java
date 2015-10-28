package com.griddynamics.kss.jpa.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.function.Consumer;

public class Transactions {
    private static final Logger LOG = LoggerFactory.getLogger(Transactions.class);

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("primary");

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                LOG.debug("Closing EntityManagerFactory");
                ENTITY_MANAGER_FACTORY.close();
            }
        });
    }

    public static void inTransaction(Consumer<EntityManager> consumer) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            consumer.accept(entityManager);
        } finally {
            if (transaction.isActive()) transaction.commit();
            entityManager.close();
        }
    }

    public static void inThrowingTransaction(ThrowingConsumer<EntityManager> consumer) throws Exception {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            consumer.accept(entityManager);
        } finally {
            if (transaction.isActive()) transaction.commit();
            entityManager.close();
        }
    }
}
