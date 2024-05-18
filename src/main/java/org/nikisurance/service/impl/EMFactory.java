package org.nikisurance.service.impl;

import jakarta.persistence.*;

public class EMFactory {
    // Using Singleton
    private static EMFactory instance;
    private final EntityManagerFactory emf;

    public static EMFactory getInstance() {
        if (instance == null) {
            instance = new EMFactory();
        }
        return instance;
    }

    private EMFactory() {
        this.emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}