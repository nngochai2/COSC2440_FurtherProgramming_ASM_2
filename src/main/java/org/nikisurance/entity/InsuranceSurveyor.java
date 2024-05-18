package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@DiscriminatorValue("INSURANCE_SURVEYOR")
@Table(name = "insurance_surveyor")
public class InsuranceSurveyor extends Provider implements Serializable {

    @OneToMany
    private Set<Claim> claims;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private InsuranceManager insuranceManager;

    public InsuranceSurveyor() {}

    public InsuranceManager getInsuranceManager() {
        return insuranceManager;
    }

    public void setInsuranceManager(InsuranceManager insuranceManager) {
        this.insuranceManager = insuranceManager;
    }

    public Set<Claim> getClaims() {
        return claims;
    }

    public void setClaims(Set<Claim> claims) {
        this.claims = claims;
    }
}
