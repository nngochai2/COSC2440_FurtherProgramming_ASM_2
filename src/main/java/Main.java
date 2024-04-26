import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.nikisurance.entity.Admin;
import persistence.CustomPersistenceUnitInfo;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), new HashMap<>());
        EntityManager em = emf.createEntityManager(); // Represents the context

        try {
            em.getTransaction().begin();

//            // Generate sample data
//            Admin admin = new Admin();
//            admin.setId(2345617);
//            admin.setUsername("Hai Ngoc");
//            admin.setPassword("123456");
//
//            em.persist(admin); // Add this to the context --> NOT AN INSERT QUERY

            // Get an entity

            Admin a1 = em.find(Admin.class, 12345);
            System.out.println(a1);

            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }
}
