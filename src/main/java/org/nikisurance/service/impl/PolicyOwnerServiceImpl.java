package org.nikisurance.service.impl;

import org.nikisurance.entity.PolicyOwner;
import org.nikisurance.repository.PolicyOwnerRepository;
import org.nikisurance.service.interfaces.PolicyOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyOwnerServiceImpl implements PolicyOwnerService {

    private final PolicyOwnerRepository policyOwnerRepository;

    @Autowired
    public PolicyOwnerServiceImpl(PolicyOwnerRepository policyOwnerRepository) {
        this.policyOwnerRepository = policyOwnerRepository;
    }

    @Override
    public PolicyOwner getPolicyOwner(Long policyOwnerId) {
        return policyOwnerRepository.findById(policyOwnerId).orElse(null);
    }

    @Override
    public PolicyOwner addPolicyOwner(PolicyOwner policyOwner) {
        return policyOwnerRepository.save(policyOwner);
    }

    @Override
    public List<PolicyOwner> getAllPolicyOwners() {
        return policyOwnerRepository.findAll();
    }

    @Override
    public void deletePolicyOwner(Long policyOwnerId) {
        policyOwnerRepository.deleteById(policyOwnerId);
    }
}
