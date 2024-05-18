package org.nikisurance.service.impl;

import org.nikisurance.entity.InsuranceCard;
import org.nikisurance.service.interfaces.InsuranceCardService;

import java.util.List;
import jakarta.persistence.TypedQuery;

public class InsuranceCardServiceImpl extends EntityRepository implements InsuranceCardService {

    @Override
    public void addInsuranceCard(InsuranceCard insuranceCard) {
        performOperation(em -> em.persist(insuranceCard));
    }

    @Override
    public InsuranceCard getInsuranceCard(Long id) {
        return performReturningOperation(em -> em.find(InsuranceCard.class, id));
    }

    @Override
    public List<InsuranceCard> getAllInsuranceCards() {
        return performReturningOperation(em -> em.createQuery("from InsuranceCard").getResultList());
    }

    @Override
    public void deleteInsuranceCard(Long id) {
        performOperation(em -> {
            InsuranceCard insuranceCard = em.find(InsuranceCard.class, id);
            if (insuranceCard != null) {
                em.remove(insuranceCard);
            } else {
                throw new IllegalArgumentException("No insurance card found with id " + id);
            }
        });
    }
}