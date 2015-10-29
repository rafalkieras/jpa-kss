package com.griddynamics.kss.jpa.worker;

import com.griddynamics.kss.jpa.InvoiceFactory;
import com.griddynamics.kss.jpa.entity.Invoice;
import com.griddynamics.kss.jpa.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.LockModeType;

import static com.griddynamics.kss.jpa.helper.Sleeper.sleep;
import static com.griddynamics.kss.jpa.helper.Transactions.inThrowingTransaction;

public class BetterHusband implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(BetterHusband.class);

    @Override
    public void run() {
        LOG.info("Husband starting transaction");

        try {
            generateTheInvoice();
        } catch (Exception e) {
            LOG.error(e.getCause().toString());
        }

        LOG.info("Husband finished his work");

    }

    public void generateTheInvoice() throws Exception {
        inThrowingTransaction(em -> {
            Order order = em.find(Order.class, 1, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

            LOG.info("My order has {} items", order.getItems().size());
            sleep(2_000);

            Invoice invoice = InvoiceFactory.createInvoice(order);
            em.persist(invoice);
        });
    }
}
