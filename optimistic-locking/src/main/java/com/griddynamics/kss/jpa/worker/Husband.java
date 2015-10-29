package com.griddynamics.kss.jpa.worker;

import com.griddynamics.kss.jpa.InvoiceFactory;
import com.griddynamics.kss.jpa.entity.Invoice;
import com.griddynamics.kss.jpa.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.griddynamics.kss.jpa.helper.Sleeper.sleep;
import static com.griddynamics.kss.jpa.helper.Transactions.inTransaction;

public class Husband implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Husband.class);

    @Override
    public void run() {
        LOG.info("Husband starting transaction");

        inTransaction(em -> {
            Order order = em.find(Order.class, 1);

            LOG.info("My order has {} items", order.getItems().size());
            sleep(2_000);

            Invoice invoice = InvoiceFactory.createInvoice(order);
            em.persist(invoice);
        });

        LOG.info("Husband finished his work");

    }

}
