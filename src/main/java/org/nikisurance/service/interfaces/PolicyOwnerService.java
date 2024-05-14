package org.nikisurance.service.interfaces;

import org.nikisurance.entity.PolicyOwner;

import java.util.List;

public interface PolicyOwnerService {
    PolicyOwner getPolicyOwner(Long policyOwnerId);
    PolicyOwner addPolicyOwner(PolicyOwner policyOwner);
    List<PolicyOwner> getAllPolicyOwners();
    void deletePolicyOwner(Long policyOwnerId);
}
