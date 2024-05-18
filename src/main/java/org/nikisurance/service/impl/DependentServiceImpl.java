package org.nikisurance.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.nikisurance.entity.Dependent;
import org.nikisurance.service.interfaces.DependentService;

import java.util.List;
import jakarta.persistence.TypedQuery;

public class DependentServiceImpl extends EntityRepository implements DependentService {

    @Override
    public void addDependent(Dependent dependent) {
        performOperation(em -> em.persist(dependent));
    }

    @Override
    public Dependent getDependent(Long id) {
        return performReturningOperation(em -> em.find(Dependent.class, id));
    }

    @Override
    public List<Dependent> getAllDependents() {
        return performReturningOperation(em -> em.createQuery("from Dependent", Dependent.class).getResultList());
    }

    @Override
    public void deleteDependent(Long id) {
        performOperation(em -> {
            Dependent dependent = em.find(Dependent.class, id);
            if (dependent != null) {
                em.remove(dependent);
            } else {
                throw new EntityNotFoundException("Dependent with id " + id + " not found");
            }
        });
    }

    @Override
    public void updateDependent(Dependent dependent) {
        performOperation(em -> em.merge(dependent));
    }
}