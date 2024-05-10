package org.nikisurance.repository.impl;

import jakarta.persistence.TypedQuery;
import org.nikisurance.entity.InsuranceCard;
import org.nikisurance.repository.EntityRepository;
import org.nikisurance.repository.repoInterface.IInsuranceCardRepository;

import java.util.List;

public class InsuranceCardRepositoryImpl extends EntityRepository implements IInsuranceCardRepository {

    @Override
    public void addInsuranceCard(InsuranceCard card) {
        em.getTransaction().begin();
        em.persist(card);
        em.getTransaction().commit();
    }

    @Override
    public InsuranceCard getInsuranceCard(int id) {
        em.getTransaction().begin();
        InsuranceCard card = em.find(InsuranceCard.class, id);
        em.getTransaction().commit();
        return card;
    }

    @Override
    public List<InsuranceCard> getAllInsuranceCards() {
        em.getTransaction().begin();
        TypedQuery<InsuranceCard> query = em.createQuery("SELECT c FROM InsuranceCard c", InsuranceCard.class);
        return query.getResultList();
    }

    @Override
    public void updateInsuranceCard(InsuranceCard card) {

    }

    @Override
    public void deleteInsuranceCard(int id) {

    }
}
