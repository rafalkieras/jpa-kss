package com.griddynamics.kss.jpa.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

public class DefaultListener {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultListener.class);

    @PostPersist
    @PostUpdate
    private void logMessage(Object entity) {
        LOG.info("{} was persisted or updated", entity);
    }

}
