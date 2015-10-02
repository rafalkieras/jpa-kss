package com.griddynamics.kss.jpa.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

public class LoggingListener {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingListener.class);

    @PrePersist
    private void prePersist(Object object) {
        LOG.info("{} is about to persist", object);
    }

    @PostPersist
    private void postPersist(Object object) {
        LOG.info("{} was persisted", object);
    }

}
