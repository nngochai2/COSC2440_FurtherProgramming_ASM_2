package org.nikisurance.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@DiscriminatorValue("InsuranceManager")
public class InsuranceManager extends Person implements Serializable {

    @OneToMany
    private List<InsuranceSurveyor> insuranceSurveyors;

    public InsuranceManager() {}

    public InsuranceManager(Long id, String name, String passwordHash, AdminRole role) {
        super(id, name, passwordHash, role);
    }

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
