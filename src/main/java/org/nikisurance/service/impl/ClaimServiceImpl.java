package org.nikisurance.service.impl;

import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;
import org.nikisurance.service.interfaces.ClaimService;

import java.util.List;

public class ClaimServiceImpl extends EntityRepository implements ClaimService {

    @Override
    public void addClaim(Claim claim) {
        em.getTransaction().begin();
        em.persist(claim);
        em.getTransaction().commit();
    }

    @Override
    public Claim getClaim(String id) {
        return em.find(Claim.class, id);
    }

    @Override
    public List<Claim> getAllClaims() {
        return em.createQuery("from Claim ", Claim.class).getResultList();
    }

    @Override
    public void deleteClaim(Claim claim) {
        em.getTransaction().begin();
        em.remove(claim);
        em.getTransaction().commit();
    }

    @Override
    public void updateClaim(Claim claim) {
        em.getTransaction().begin();
        em.merge(claim);
        em.getTransaction().commit();
    }

    @Override
    public long getCountByStatus(ClaimStatus status) {
        return em.createQuery("select count(c) from Claim c where c.status = :status", Long.class)
                .setParameter("status", status)
                .getSingleResult();
    }
}