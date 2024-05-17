package org.nikisurance.service.impl;

import org.nikisurance.entity.Beneficiary;
import org.nikisurance.service.interfaces.BeneficiaryService;

import java.util.List;
import jakarta.persistence.TypedQuery;

public class BeneficiaryServiceImpl extends EntityRepository implements BeneficiaryService {

    @Override
    public void addBeneficiary(Beneficiary b) {
        em.getTransaction().begin();
        em.persist(b);
        em.getTransaction().commit();
    }

    @Override
    public Beneficiary getBeneficiary(Long id) {
        return em.find(Beneficiary.class, id);
    }

    @Override
    public List<Beneficiary> getAllBeneficiaries() {
        TypedQuery<Beneficiary> query = em.createQuery("from Beneficiary", Beneficiary.class);
        return query.getResultList();
    }

    @Override
    public void deleteBeneficiary(Long id) {
        Beneficiary b = getBeneficiary(id);
        if (b != null) {
            em.getTransaction().begin();
            em.remove(b);
            em.getTransaction().commit();
        }
    }
}