package org.nikisurance.service.interfaces;

import org.nikisurance.entity.Dependent;

import java.util.List;

public interface DependentService {
    void addDependent(Dependent dependent);
    Dependent getDependent(Long id);
    List<Dependent> getAllDependents();
    void deleteDependent(Long id);
    void updateDependent(Dependent dependent);
    List<Dependent> getDependentsByPolicyHolderId(Long policyHolderId);
}
