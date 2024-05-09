package org.nikisurance.repository;

public class EntityManagerFactory {
    // Using Singleton
    private static EntityManagerFactory instance;
    private static EntityManagerFactory emf;

    private static EntityManagerFactory getInstance() {
        if (instance == null) {
            instance = new EntityManagerFactory();
        }
        return instance;
    }

    private EntityManagerFactory() {}
}
