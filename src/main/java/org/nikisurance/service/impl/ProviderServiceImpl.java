package org.nikisurance.service.impl;

import org.nikisurance.entity.Provider;
import org.nikisurance.service.interfaces.ProviderService;

import java.util.List;
import jakarta.persistence.TypedQuery;

public class ProviderServiceImpl extends EntityRepository implements ProviderService {

    @Override
    public void addProvider(Provider provider) {
        em.getTransaction().begin();
        em.persist(provider);
        em.getTransaction().commit();
    }

    @Override
    public Provider getProvider(Long id) {
        return em.find(Provider.class, id);
    }

    @Override
    public List<Provider> getAllProviders() {
        TypedQuery<Provider> query = em.createQuery("from Provider", Provider.class);
        return query.getResultList();
    }

    @Override
    public void deleteProvider(Long id) {
        Provider provider = getProvider(id);
        if (provider != null) {
            em.getTransaction().begin();
            em.remove(provider);
            em.getTransaction().commit();
        }
    }

    @Override
    public int countInsuranceManagers() {
        Long count = em.createQuery("select count(p) from Provider p where p.role = 'InsuranceManager'", Long.class).getSingleResult();
        return count.intValue();
    }

    @Override
    public int countInsuranceProvider() {
        Long count = em.createQuery("select count(p) from Provider p where p.role = 'InsuranceProvider'", Long.class).getSingleResult();
        return count.intValue();
    }
}