package org.nikisurance.service.impl;

import org.nikisurance.entity.PolicyHolder;
import org.nikisurance.service.interfaces.PolicyHolderService;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.persistence.TypedQuery;

public class PolicyHolderServiceImpl extends EntityRepository implements PolicyHolderService {

    @Override
    public void addPolicyHolder(PolicyHolder policyHolder) {
        performOperation(em -> em.persist(policyHolder));
    }

    @Override
    public PolicyHolder getPolicyHolder(Long id) {
        return performReturningOperation(em -> em.find(PolicyHolder.class, id));
    }

    @Override
    public List<PolicyHolder> getAllPolicyHolders() {
        return performReturningOperation(em -> em.createQuery("from PolicyHolder", PolicyHolder.class).getResultList());
    }

    @Override
    public void deletePolicyHolder(Long id) {
        performOperation(em -> {
            PolicyHolder policyHolder = em.find(PolicyHolder.class, id);
            if (policyHolder != null) {
                em.remove(policyHolder);
            } else {
                throw new IllegalArgumentException("PolicyHolder with id " + id + " not found");
            }
        });
    }
}