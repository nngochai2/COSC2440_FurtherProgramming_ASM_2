package org.nikisurance.repository.repoInterface;

import org.nikisurance.entity.PolicyHolder;

import java.util.List;

public interface IPolicyHolderRepository {
    void addPolicyHolder(PolicyHolder policyHolder);
    PolicyHolder findPolicyHolder(Long policyHolderId);
    List<PolicyHolder> findAllPolicyHolders();
    void updatePolicyHolder(PolicyHolder policyHolder);
    void removePolicyHolder(Long policyHolderId);
}
