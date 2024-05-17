package org.nikisurance.service.impl;

import org.nikisurance.entity.Dependent;
import org.nikisurance.service.interfaces.DependentService;

import java.util.List;
import jakarta.persistence.TypedQuery;

public class DependentServiceImpl extends EntityRepository implements DependentService {

    @Override
    public Dependent addDependent(Dependent dependent) {
        em.getTransaction().begin();
        em.persist(dependent);
        em.getTransaction().commit();
        return dependent;
    }

    @Override
    public Dependent getDependent(Long id) {
        return em.find(Dependent.class, id);
    }

    @Override
    public List<Dependent> getAllDependents() {
        TypedQuery<Dependent> query = em.createQuery("from Dependent", Dependent.class);
        return query.getResultList();
    }

    @Override
    public void deleteDependent(Long id) {
        Dependent dependent = getDependent(id);
        if (dependent != null) {
            em.getTransaction().begin();
            em.remove(dependent);
            em.getTransaction().commit();
        }
    }

    @Override
    public Dependent updateDependent(Dependent dependent) {
        em.getTransaction().begin();
        Dependent updatedDependent = em.merge(dependent);
        em.getTransaction().commit();
        return updatedDependent;
    }
}