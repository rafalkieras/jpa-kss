package com.griddynamics.kss.jpa;

import com.griddynamics.kss.jpa.entity.Invoice;
import com.griddynamics.kss.jpa.entity.Order;

public class InvoiceFactory {

    public static Invoice createInvoice(Order order) {
        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setTotal(order.getTotal());
        invoice.setId(1);

        return invoice;
    }

}
