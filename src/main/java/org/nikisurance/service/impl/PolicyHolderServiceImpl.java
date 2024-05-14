package org.nikisurance.service.impl;

import org.nikisurance.entity.PolicyHolder;
import org.nikisurance.repository.PolicyHolderRepository;
import org.nikisurance.service.interfaces.PolicyHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyHolderServiceImpl implements PolicyHolderService {

    private final PolicyHolderRepository policyHolderRepository;

    @Autowired
    public PolicyHolderServiceImpl(PolicyHolderRepository policyHolderRepository) {
        this.policyHolderRepository = policyHolderRepository;
    }

    @Override
    public PolicyHolder getPolicyHolder(Long id) {
        return policyHolderRepository.findById(id).orElse(null);
    }

    @Override
    public PolicyHolder addPolicyHolder(PolicyHolder policyHolder) {
        return policyHolderRepository.save(policyHolder);
    }

    @Override
    public List<PolicyHolder> getAllPolicyHolders() {
        return policyHolderRepository.findAll();
    }

    @Override
    public void deletePolicyHolder(Long id) {
        policyHolderRepository.deleteById(id);
    }
}
