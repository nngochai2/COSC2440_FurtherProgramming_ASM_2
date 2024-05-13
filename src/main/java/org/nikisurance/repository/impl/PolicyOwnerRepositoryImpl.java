package org.nikisurance.repository.impl;

import org.nikisurance.entity.PolicyOwner;
import org.nikisurance.repository.EntityRepository;
import org.nikisurance.repository.repoInterface.IPolicyOwnerRepository;

public class PolicyOwnerRepositoryImpl extends EntityRepository implements IPolicyOwnerRepository {

    @Override
    public void addPolicyOwner(PolicyOwner policyOwner) {
        em.getTransaction().begin();
        em.persist(policyOwner);
        em.getTransaction().commit();
    }
}
