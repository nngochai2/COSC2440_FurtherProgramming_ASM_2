package org.nikisurance.repository.impl;

import jakarta.persistence.TypedQuery;
import org.nikisurance.entity.InsuranceManager;
import org.nikisurance.repository.EntityRepository;
import org.nikisurance.repository.repoInterface.IInsuranceManagerRepository;

import java.lang.reflect.Type;
import java.util.List;

public class InsuranceManagerRepositoryImpl extends EntityRepository implements IInsuranceManagerRepository {
    @Override
    public void addInsuranceManager(InsuranceManager insuranceManager) {
        em.getTransaction().begin();
        em.persist(insuranceManager);
        em.getTransaction().commit();
    }

    @Override
    public InsuranceManager getInsuranceManager(int id) {
        em.getTransaction().begin();
        InsuranceManager insuranceManager = em.find(InsuranceManager.class, id);
        em.getTransaction().commit();
        return insuranceManager;
    }

    @Override
    public void updateInsuranceManager(InsuranceManager insuranceManager) {
        em.getTransaction().begin();
        em.merge(insuranceManager);
        em.getTransaction().commit();
    }

    @Override
    public void deleteInsuranceManager(InsuranceManager insuranceManager) {
        em.getTransaction().begin();
        em.remove(insuranceManager);
        em.getTransaction().commit();
    }

    @Override
    public List<InsuranceManager> getAllInsuranceManagers() {
        em.getTransaction().begin();
        TypedQuery<InsuranceManager> query = em.createQuery("select m from InsuranceManager m", InsuranceManager.class);
        List<InsuranceManager> insuranceManagers = query.getResultList();
        em.getTransaction().commit();
        return insuranceManagers;
    }
}
