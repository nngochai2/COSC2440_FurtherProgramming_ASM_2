package org.nikisurance.repository.impl;

import jakarta.persistence.TypedQuery;
import org.nikisurance.entity.PolicyHolder;
import org.nikisurance.repository.EntityRepository;
import org.nikisurance.repository.repoInterface.IPolicyHolderRepository;

import java.util.List;

public class PolicyHolderRepositoryImpl extends EntityRepository implements IPolicyHolderRepository {
    @Override
    public void addPolicyHolder(PolicyHolder policyHolder) {
        em.getTransaction().begin();
        em.persist(policyHolder);
        em.getTransaction().commit();
    }

    @Override
    public PolicyHolder findPolicyHolder(Long policyHolderId) {
        return em.find(PolicyHolder.class, policyHolderId);
    }

    @Override
    public List<PolicyHolder> findAllPolicyHolders() {
        em.getTransaction().begin();
        TypedQuery<PolicyHolder> query = em.createQuery("select p from PolicyHolder p", PolicyHolder.class);
        return query.getResultList();
    }

    @Override
    public void updatePolicyHolder(PolicyHolder policyHolder) {
        PolicyHolder policyHolderToUpdate = findPolicyHolder(policyHolder.getId());
        em.getTransaction().begin();
        policyHolderToUpdate.setFullName(policyHolder.getFullName());
        policyHolderToUpdate.setPassword(policyHolder.getPassword());
        policyHolderToUpdate.setBankName(policyHolderToUpdate.getBankName());
        policyHolderToUpdate.setBankNumber(policyHolderToUpdate.getBankNumber());

        em.merge(policyHolderToUpdate);
        em.getTransaction().commit();
    }

    @Override
    public void removePolicyHolder(Long policyHolderId) {
        em.getTransaction().begin();
        PolicyHolder policyHolderToRemove = findPolicyHolder(policyHolderId);
        em.remove(policyHolderToRemove);
        em.getTransaction().commit();
    }
}
