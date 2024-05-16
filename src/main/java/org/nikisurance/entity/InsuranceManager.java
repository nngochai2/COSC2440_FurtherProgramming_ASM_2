package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@DiscriminatorValue("INSURANCE_MANAGER")
@Table(name = "insurance_manager")
public class InsuranceManager extends Provider implements Serializable {
    @OneToMany
    private List<InsuranceSurveyor> insuranceSurveyors;

    public InsuranceManager() {}

    public List<InsuranceSurveyor> getInsuranceSurveyors() {
        return insuranceSurveyors;
    }

    public void setInsuranceSurveyors(List<InsuranceSurveyor> insuranceSurveyors) {
        this.insuranceSurveyors = insuranceSurveyors;
    }

    public void addInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        this.insuranceSurveyors.add(insuranceSurveyor);
    }

    public void removeInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        this.insuranceSurveyors.remove(insuranceSurveyor);
    }
}
