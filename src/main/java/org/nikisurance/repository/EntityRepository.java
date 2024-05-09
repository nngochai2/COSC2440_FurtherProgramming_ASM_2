package org.nikisurance.repository;

import jakarta.persistence.EntityManager;

public abstract class EntityRepository {
    protected final EntityManager em;

    protected EntityRepository() {
        em = EMFactory.getInstance().getEntityManager();
    }

    public void close() {
        em.close();
    }
}
