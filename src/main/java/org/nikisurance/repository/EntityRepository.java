package org.nikisurance.repository;

import jakarta.persistence.EntityManager;

public abstract class EntityRepository {
    protected final EntityManager em;

    protected EntityRepository(EntityManager em) {
        this.em = em;
    }

    public void close() {
        em.close();
    }
}
