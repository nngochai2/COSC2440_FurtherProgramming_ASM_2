package org.nikisurance.repository.impl;

import jakarta.persistence.TypedQuery;
import org.nikisurance.entity.Beneficiary;
import org.nikisurance.repository.EntityRepository;
import org.nikisurance.repository.repoInterface.IBeneficiaryRepository;

import java.util.List;

public class BeneficiaryRepositoryImpl extends EntityRepository implements IBeneficiaryRepository {
    @Override
    public void addBeneficiary(Beneficiary beneficiary) {
        try {
            em.getTransaction().begin();
            // Check if the entity is already managed
            if (!em.contains(beneficiary)) {
                // If not, merge it to ensure it's in a managed state
                beneficiary = em.merge(beneficiary);
            }
            // Persist the entity
            em.persist(beneficiary);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // Handle or log the exception
        }
    }

    @Override
    public Beneficiary getBeneficiary(int id) {
        em.getTransaction().begin();
        return em.find(Beneficiary.class, id);
    }

    @Override
    public List<Beneficiary> getBeneficiaries() {
        em.getTransaction().begin();
        TypedQuery<Beneficiary> query = em.createQuery("select b from Beneficiary b", Beneficiary.class);
        return query.getResultList();
    }

    @Override
    public void deleteBeneficiary(int id) {
        em.getTransaction().begin();
        em.remove(em.find(Beneficiary.class, id));
        em.getTransaction().commit();
    }
}
