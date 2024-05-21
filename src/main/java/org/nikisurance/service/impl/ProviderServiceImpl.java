package org.nikisurance.service.impl;

import org.nikisurance.entity.Provider;
import org.nikisurance.entity.ProviderRole;
import org.nikisurance.service.interfaces.ProviderService;

import java.util.List;
import jakarta.persistence.TypedQuery;

public class ProviderServiceImpl extends EntityRepository implements ProviderService {

    @Override
    public void addProvider(Provider provider) {
        performOperation(em -> em.persist(provider));
    }

    @Override
    public Provider getProvider(Long id) {
        return performReturningOperation(em -> em.find(Provider.class, id));
    }

    @Override
    public List<Provider> getAllProviders() {
        return performReturningOperation(em -> em.createQuery("from Provider", Provider.class).getResultList());
    }

    @Override
    public void deleteProvider(Long id) {
        performOperation(em -> {
            Provider provider = em.find(Provider.class, id);
            if (provider != null) {
                em.remove(provider);
            } else {
                throw new IllegalArgumentException("Provider with id " + id + " not found");
            }
        });
    }

    @Override
    public void updateProvider(Provider provider) {
        performOperation(em -> em.merge(provider));
    }

    @Override
    public int countInsuranceManagers() {
        return performReturningOperation(em -> em.createQuery(
                        "select count(p) from Provider p where p.role = :role", Long.class)
                .setParameter("role", ProviderRole.INSURANCE_MANAGER)
                .getSingleResult()).intValue();
    }

    @Override
    public int countInsuranceSurveyors() {
        return performReturningOperation(em -> em.createQuery(
                        "select count(p) from Provider p where p.role = :role", Long.class)
                .setParameter("role", ProviderRole.INSURANCE_SURVEYOR)
                .getSingleResult()).intValue();
    }
}