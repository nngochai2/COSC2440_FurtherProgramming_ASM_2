package org.nikisurance.service.impl;

import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;
import org.nikisurance.service.interfaces.ClaimService;

import java.util.List;

public class ClaimServiceImpl extends EntityRepository implements ClaimService {

    @Override
    public void addClaim(Claim claim) {
        performOperation(em -> em.persist(claim));
    }

    @Override
    public Claim getClaim(String id) {
        return performReturningOperation(em -> em.find(Claim.class, id));
    }

    @Override
    public List<Claim> getAllClaims() {
        return performReturningOperation(em -> em.createQuery("from Claim", Claim.class).getResultList());
    }

    @Override
    public void deleteClaim(Claim claim) {
        performOperation(em -> {
            Claim managedClaim = em.merge(claim);
            em.remove(managedClaim);
        });
    }

    @Override
    public void updateClaim(Claim claim) {
        performOperation(em -> em.merge(claim));
    }

    @Override
    public long getCountByStatus(ClaimStatus status) {
        return performReturningOperation(em -> em.createQuery("select count(c) from Claim c where c.status = :status", Long.class)
                .setParameter("status", status)
                .getSingleResult());
    }
}