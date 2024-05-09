package org.nikisurance.repository.impl;

import jakarta.persistence.EntityManager;
import org.nikisurance.entity.Claim;
import org.nikisurance.repository.EntityRepository;
import org.nikisurance.repository.repoInterface.IClaimRepository;

import java.util.List;

public class ClaimRepositoryImpl extends EntityRepository implements IClaimRepository {
    private final EntityManager em;

    public ClaimRepositoryImpl(EntityManager em, EntityManager em1) {
        super(em);
        this.em = em1;
    }

    @Override
    public Claim getClaimById(int id) {
        return em.find(Claim.class, id);
    }

    @Override
    public void saveClaim(Claim claim) {
        em.getTransaction().begin();

        em.persist(claim);

        em.getTransaction().commit();
    }

    @Override
    public void deleteClaim(Claim claim) {
        if (em.contains(claim)) {
            em.remove(claim);
        } else {
            em.merge(claim);
        }
    }

    @Override
    public List<Claim> getAllClaims() {
        return em.createQuery("select c from Claim c", Claim.class).getResultList();
    }

    @Override
    public List<Claim> getClaimsByCustomer(int customerId) {
        return List.of();
    }

    @Override
    public List<Claim> getClaimsFromDateToDate(int fromDate, int toDate) {
        return List.of();
    }


}
