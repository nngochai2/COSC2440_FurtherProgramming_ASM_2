package org.nikisurance.service.impl;

import org.nikisurance.entity.PolicyHolder;
import org.nikisurance.service.interfaces.PolicyHolderService;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.persistence.TypedQuery;

@Service
public class PolicyHolderServiceImpl extends EntityRepository implements PolicyHolderService {

    @Override
    public void addPolicyHolder(PolicyHolder policyHolder) {
        em.getTransaction().begin();
        em.persist(policyHolder);
        em.getTransaction().commit();
    }

    @Override
    public PolicyHolder getPolicyHolder(Long id) {
        return em.find(PolicyHolder.class, id);
    }

    @Override
    public List<PolicyHolder> getAllPolicyHolders() {
        TypedQuery<PolicyHolder> query = em.createQuery("from PolicyHolder", PolicyHolder.class);
        return query.getResultList();
    }

    @Override
    public void deletePolicyHolder(Long id) {
        PolicyHolder policyHolder = getPolicyHolder(id);
        if (policyHolder != null) {
            em.getTransaction().begin();
            em.remove(policyHolder);
            em.getTransaction().commit();
        }
    }
}