package org.nikisurance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.nikisurance.entity.Admin;
import persistence.CustomPersistenceUnitInfo;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<String, Object> properties = new HashMap<>();

        // Show the queries
        properties.put("hibernate.show_sql", "true");

        // Create the context
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), properties);

        // Represents the manager of the context
        EntityManager em = emf.createEntityManager();

        try {
            // Start the transaction
            em.getTransaction().begin();

            // Get an entity
            Admin admin = new Admin();
            admin.setId(123456);
            admin.setUsername("Hoang");
            admin.setPassword("123456");
            em.persist(admin); // Add this to the context --> NOT AN INSERT QUERY

            em.getTransaction().commit(); // End of the transaction
        } finally {
            em.close();
        }

    }
}
