package org.nikisurance.service.impl;

import org.nikisurance.entity.Beneficiary;
import org.nikisurance.service.interfaces.BeneficiaryService;

import java.util.List;

public class BeneficiaryServiceImpl extends EntityRepository implements BeneficiaryService {

    @Override
    public void addBeneficiary(Beneficiary beneficiary) {
        performOperation(em -> em.persist(beneficiary));
    }

    @Override
    public Beneficiary getBeneficiary(Long id) {
        return performReturningOperation(em -> em.find(Beneficiary.class, id));
    }

    @Override
    public List<Beneficiary> getAllBeneficiaries() {
        return performReturningOperation(em -> em.createQuery("select b from Beneficiary b", Beneficiary.class).getResultList());
    }

    @Override
    public void deleteBeneficiary(Long id) {
        performOperation(em -> {
            Beneficiary beneficiary = em.find(Beneficiary.class, id);
            if (beneficiary != null) {
                em.remove(beneficiary);
            } else {
                throw new IllegalArgumentException("No beneficiary found with id: " + id);
            }
        });
    }

    @Override
    public void updateBeneficiary(Beneficiary b) {
        performOperation(em -> em.merge(b));
    }
}