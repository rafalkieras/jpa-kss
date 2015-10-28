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
import static com.griddynamics.kss.jpa.helper.Transactions.inTransaction;

public class Husband implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Husband.class);

    @Override
    public void run() {
        LOG.info("I'll generate the invoice");

        inTransaction(em -> {
            Map<String, Object> properties = new HashMap<>();
            properties.put("javax.persistence.lock.scope", PessimisticLockScope.EXTENDED);
            Order order = em.find(Order.class, 1, LockModeType.PESSIMISTIC_WRITE, properties);
            em.lock(order, LockModeType.PESSIMISTIC_WRITE, properties);
            LOG.info("My order has {} items", order.getItems().size());
            sleep(1000);

            Invoice invoice = InvoiceFactory.createInvoice(order);
            em.persist(invoice);
        });

    }
}
