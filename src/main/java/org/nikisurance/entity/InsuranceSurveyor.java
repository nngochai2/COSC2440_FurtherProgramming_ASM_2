package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@DiscriminatorValue("InsuranceSurveyor")
public class InsuranceSurveyor extends Person implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    private InsuranceManager insuranceManager;

    public InsuranceSurveyor() {}

    public InsuranceSurveyor(Long id, String name, String passwordHash, AdminRole role) {
        super(id, name, passwordHash, role);
    }

    public InsuranceManager getInsuranceManager() {
        return insuranceManager;
    }

    public void setInsuranceManager(InsuranceManager insuranceManager) {
        this.insuranceManager = insuranceManager;
    }
}
