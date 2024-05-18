package org.nikisurance.service.impl;

import org.nikisurance.entity.PolicyOwner;
import org.nikisurance.service.interfaces.PolicyOwnerService;

import java.util.List;
import jakarta.persistence.TypedQuery;

public class PolicyOwnerServiceImpl extends EntityRepository implements PolicyOwnerService {

    @Override
    public void addPolicyOwner(PolicyOwner policyOwner) {
        performOperation(em -> em.persist(policyOwner));
    }

    @Override
    public PolicyOwner getPolicyOwner(Long policyOwnerId) {
        return performReturningOperation(em -> em.find(PolicyOwner.class, policyOwnerId));
    }

    @Override
    public List<PolicyOwner> getAllPolicyOwners() {
        return performReturningOperation(em -> em.createQuery("from PolicyOwner", PolicyOwner.class).getResultList());
    }

    @Override
    public void deletePolicyOwner(Long policyOwnerId) {
        performOperation(em -> {
            PolicyOwner policyOwner = em.find(PolicyOwner.class, policyOwnerId);
            if (policyOwner != null) {
                em.remove(policyOwner);
            } else {
                throw new IllegalArgumentException("PolicyOwner not found");
            }
        });
    }
}