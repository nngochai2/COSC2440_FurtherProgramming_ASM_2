package org.nikisurance.repository.repoInterface;

import org.nikisurance.entity.InsuranceManager;

import java.util.List;

public interface IInsuranceManagerRepository {
    void addInsuranceManager(InsuranceManager insuranceManager);
    InsuranceManager getInsuranceManager(int id);
    void updateInsuranceManager(InsuranceManager insuranceManager);
    void deleteInsuranceManager(InsuranceManager insuranceManager);
    List<InsuranceManager> getAllInsuranceManagers();
}
