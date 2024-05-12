package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@DiscriminatorValue("InsuranceSurveyor")
public class InsuranceSurveyor extends Provider implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    private InsuranceManager insuranceManager;

    public InsuranceSurveyor() {}

    public InsuranceManager getInsuranceManager() {
        return insuranceManager;
    }

    public void setInsuranceManager(InsuranceManager insuranceManager) {
        this.insuranceManager = insuranceManager;
    }
}
