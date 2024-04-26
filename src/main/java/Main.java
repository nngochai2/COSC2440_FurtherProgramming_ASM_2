import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.model.Admin;
import persistence.CustomerPersistenceUnitInfo;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomerPersistenceUnitInfo(), new HashMap<>());
        EntityManager em = emf.createEntityManager(); // Represents the context

        try {
            em.getTransaction().begin();

            Admin admin = new Admin();
            admin.setId(1234567);
            admin.setUsername("Hai Ngoc");
            admin.setPassword("123456");

            em.persist(admin); // Add this to the context --> NOT AN INSERT QUERY

            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }

    }
}
