package org.nikisurance.service.impl;

import org.nikisurance.entity.InsuranceCard;
import org.nikisurance.service.interfaces.InsuranceCardService;

import java.util.List;
import jakarta.persistence.TypedQuery;

public class InsuranceCardServiceImpl extends EntityRepository implements InsuranceCardService {

    @Override
    public InsuranceCard addInsuranceCard(InsuranceCard insuranceCard) {
        em.getTransaction().begin();
        em.persist(insuranceCard);
        em.getTransaction().commit();
        return insuranceCard;
    }

    @Override
    public InsuranceCard getInsuranceCard(Long id) {
        return em.find(InsuranceCard.class, id);
    }

    @Override
    public List<InsuranceCard> getAllInsuranceCards() {
        TypedQuery<InsuranceCard> query = em.createQuery("from InsuranceCard", InsuranceCard.class);
        return query.getResultList();
    }

    @Override
    public void deleteInsuranceCard(Long id) {
        InsuranceCard insuranceCard = getInsuranceCard(id);
        if (insuranceCard != null) {
            em.getTransaction().begin();
            em.remove(insuranceCard);
            em.getTransaction().commit();
        }
    }
}