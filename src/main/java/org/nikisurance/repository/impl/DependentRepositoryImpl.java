package org.nikisurance.repository.impl;

import org.nikisurance.entity.Dependent;
import org.nikisurance.repository.EntityRepository;
import org.nikisurance.repository.repoInterface.IDependentRepository;

import java.util.List;

/**
 * @author Team 15
 */

public class DependentRepositoryImpl extends EntityRepository implements IDependentRepository {

    @Override
    public void addDependent(Dependent dependent) {
        em.getTransaction().begin();
        em.persist(dependent);
        em.getTransaction().commit();
    }

    @Override
    public void removeDependent(Dependent dependent) {
        em.getTransaction().begin();
        em.remove(em.merge(dependent));
        em.getTransaction().commit();
    }

    @Override
    public void updateDependent(Dependent dependent) {
        em.getTransaction().begin();
        em.merge(dependent);
        em.getTransaction().commit();
    }

    @Override
    public Dependent getDependent(int id) {
        return em.find(Dependent.class, id);
    }

    @Override
    public List<Dependent> getAllDependents() {
        em.getTransaction().begin();
        return em.createQuery("from Dependent", Dependent.class).getResultList();
    }
}
