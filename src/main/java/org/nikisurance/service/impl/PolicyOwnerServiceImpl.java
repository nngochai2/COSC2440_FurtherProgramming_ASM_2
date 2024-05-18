package org.nikisurance.service.impl;

import org.nikisurance.entity.PolicyOwner;
import org.nikisurance.service.interfaces.PolicyOwnerService;

import java.util.List;
import jakarta.persistence.TypedQuery;

public class PolicyOwnerServiceImpl extends EntityRepository implements PolicyOwnerService {

    @Override
    public void addPolicyOwner(PolicyOwner policyOwner) {
        em.getTransaction().begin();
        em.persist(policyOwner);
        em.getTransaction().commit();
    }

    @Override
    public PolicyOwner getPolicyOwner(Long policyOwnerId) {
        return em.find(PolicyOwner.class, policyOwnerId);
    }

    @Override
    public List<PolicyOwner> getAllPolicyOwners() {
        TypedQuery<PolicyOwner> query = em.createQuery("from PolicyOwner", PolicyOwner.class);
        return query.getResultList();
    }

    @Override
    public void deletePolicyOwner(Long policyOwnerId) {
        PolicyOwner policyOwner = getPolicyOwner(policyOwnerId);
        if (policyOwner != null) {
            em.getTransaction().begin();
            em.remove(policyOwner);
            em.getTransaction().commit();
        }
    }
}