package org.nikisurance.service.impl;

import org.nikisurance.entity.PolicyHolder;
import org.nikisurance.service.interfaces.PolicyHolderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyHolderServiceImpl implements PolicyHolderService {
    @Override
    public PolicyHolder getPolicyHolder(Long id) {
        return null;
    }

    @Override
    public PolicyHolder addPolicyHolder(PolicyHolder policyHolder) {
        return null;
    }

    @Override
    public List<PolicyHolder> getAllPolicyHolders() {
        return List.of();
    }

    @Override
    public void deletePolicyHolder(Long id) {

    }
}
