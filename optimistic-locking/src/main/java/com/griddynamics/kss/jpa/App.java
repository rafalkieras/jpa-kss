package com.griddynamics.kss.jpa;


import com.griddynamics.kss.jpa.entity.Invoice;
import com.griddynamics.kss.jpa.entity.Item;
import com.griddynamics.kss.jpa.entity.Order;
import com.griddynamics.kss.jpa.worker.BetterHusband;
import com.griddynamics.kss.jpa.worker.Husband;
import com.griddynamics.kss.jpa.worker.Wife;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.griddynamics.kss.jpa.helper.Sleeper.sleep;
import static com.griddynamics.kss.jpa.helper.Transactions.inTransaction;

public class App
{
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        inTransaction(em -> {
            Order order = new Order(1);
            em.persist(order);

            Item item1 = new Item(1, BigDecimal.ONE, "Item1");
            item1.setOrder(order);
            em.persist(item1);

            Item item2 = new Item(2, BigDecimal.TEN, "Item2");
            item2.setOrder(order);
            em.persist(item2);
        });


        ExecutorService service = Executors.newFixedThreadPool(2);

        service.submit(new BetterHusband());
        service.submit(new Wife());


        sleep(3_000);

        inTransaction(em -> {
            Order order = em.find(Order.class, 1);
            Invoice invoice = em.find(Invoice.class, 1);

            if (invoice != null) {
                LOG.info("Invoice total {}", invoice.getTotal());
            }

            LOG.info("Order total {}", order.getTotal());
            LOG.info("Order version: {}", order.getVersion());

        });

        System.exit(0);
    }
}
