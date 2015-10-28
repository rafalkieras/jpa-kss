package com.griddynamics.kss.jpa.worker;

import com.griddynamics.kss.jpa.entity.Item;
import com.griddynamics.kss.jpa.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static com.griddynamics.kss.jpa.helper.Sleeper.sleep;
import static com.griddynamics.kss.jpa.helper.Transactions.inTransaction;

public class Wife implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Husband.class);

    @Override
    public void run() {
        sleep(500);

        LOG.info("I'll add some more items to the order");

        inTransaction(em -> {
//            Order order = em.getReference(Order.class, 1);
//            Item item3 = new Item(3, BigDecimal.TEN, "Item3");
//            item3.setOrder(order);
//            em.persist(item3);

            Order order = em.find(Order.class, 1);
            Item item = em.find(Item.class, 2);
            order.getItems().remove(item);
        });

        LOG.info("Item added");

    }
}
