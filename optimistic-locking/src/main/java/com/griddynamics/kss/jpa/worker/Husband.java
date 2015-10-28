package com.griddynamics.kss.jpa.worker;

import com.griddynamics.kss.jpa.InvoiceFactory;
import com.griddynamics.kss.jpa.entity.Invoice;
import com.griddynamics.kss.jpa.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockScope;

import java.util.HashMap;
import java.util.Map;

import static com.griddynamics.kss.jpa.helper.Sleeper.sleep;
import static com.griddynamics.kss.jpa.helper.Transactions.inThrowingTransaction;
import static com.griddynamics.kss.jpa.helper.Transactions.inTransaction;

public class Husband implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Husband.class);

    @Override
    public void run() {
        LOG.info("Husband starting transaction");

        try {
            generateTheInvoice();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            try {
                generateTheInvoice();
            } catch (Exception e1) {
                LOG.error(e.getMessage());
            }
        }

        LOG.info("Husband committed his transaction");

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
