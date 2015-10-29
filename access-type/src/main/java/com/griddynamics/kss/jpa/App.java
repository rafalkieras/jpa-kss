package com.griddynamics.kss.jpa;


import com.griddynamics.kss.jpa.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static com.griddynamics.kss.jpa.helper.Transactions.inTransaction;

public class App
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        inTransaction(em -> {
            em.persist(new Product(1, "XYZ123", new BigDecimal("199.99"), new BigDecimal("0.00")));
        });

        inTransaction(em -> {
            Product product = em.find(Product.class, 1);

            product.setDiscount(new BigDecimal("5.00"));
        });

        inTransaction(em -> {
            Product product = em.createQuery("from Product where code = :code", Product.class)
                    .setParameter("code", "XYZ123")
                    .getSingleResult();

            LOG.info("Price of the product is: {}", product.getPrice());
        });

        inTransaction(em -> {
            Product product = em.createQuery("from Product where code = :code", Product.class)
                    .setParameter("code", "XYZ123")
                    .getSingleResult();

            LOG.info("Price of the product is: {}", product.getPrice());
        });


        System.exit(0);
    }
}
