package org.nikisurance.repository.impl;

import jakarta.persistence.TypedQuery;
import org.nikisurance.entity.Beneficiary;
import org.nikisurance.repository.EntityRepository;
import org.nikisurance.repository.repoInterface.IBeneficiaryRepository;

import java.util.List;

public class BeneficiaryRepositoryImpl extends EntityRepository implements IBeneficiaryRepository {
    @Override
    public void addBeneficiary(Beneficiary beneficiary) {
        em.getTransaction().begin();
        em.persist(beneficiary);
        em.getTransaction().commit();
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
