package org.nikisurance.service.impl;

import jakarta.persistence.EntityManager;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class EntityRepository {
    protected EntityManager getEntityManager() {
        return EMFactory.getInstance().getEntityManager();
    };

    protected void performOperation(Consumer<EntityManager> operation) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            operation.accept(em);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    protected <T> T performReturningOperation(Function<EntityManager, T> operation) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            T result = operation.apply(em);
            em.getTransaction().commit();
            return result;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}