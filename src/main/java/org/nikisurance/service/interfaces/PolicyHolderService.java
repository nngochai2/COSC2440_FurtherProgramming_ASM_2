package org.nikisurance.service.interfaces;

import org.nikisurance.entity.PolicyHolder;

import java.util.List;

public interface PolicyHolderService {
    PolicyHolder getPolicyHolder(Long id);
    void addPolicyHolder(PolicyHolder policyHolder);
    List<PolicyHolder> getAllPolicyHolders();
    void deletePolicyHolder(Long id);
}