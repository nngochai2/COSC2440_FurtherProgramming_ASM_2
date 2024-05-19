package org.nikisurance.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;
import org.nikisurance.entity.Dependent;
import org.nikisurance.service.interfaces.ClaimService;
import org.nikisurance.service.interfaces.DependentService;

import java.util.ArrayList;
import java.util.List;

public class ClaimServiceImpl extends EntityRepository implements ClaimService {

    private final DependentService dependentService;

    public ClaimServiceImpl() {
        this.dependentService = new DependentServiceImpl();
    }

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

    @Override
    public List<Claim> getClaimsForPolicyHolderAndDependents(Long policyHolderId) {
        List<Long> ids = new ArrayList<>();
        ids.add(policyHolderId);

        // Fetch dependents and add their Ids
        List<Dependent> dependents = dependentService.getDependentsByPolicyHolderId(policyHolderId);
        for (Dependent dependent : dependents) {
            ids.add(dependent.getId());
        }

        return performReturningOperation(em -> em.createQuery("SELECT c FROM Claim c WHERE c.beneficiaryId IN :ids", Claim.class)
                .setParameter("ids", ids)
                .getResultList());
    }

    @Override
    public List<Claim> getClaimsForBeneficiaries(List<Long> beneficiaryIds) {
        return performReturningOperation(em -> em.createQuery(
                        "SELECT c FROM Claim c WHERE c.beneficiaryId IN :ids", Claim.class)
                .setParameter("ids", beneficiaryIds)
                .getResultList());
    }
}