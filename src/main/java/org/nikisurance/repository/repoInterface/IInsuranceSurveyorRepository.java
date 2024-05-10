package org.nikisurance.repository.repoInterface;

import org.nikisurance.entity.InsuranceSurveyor;

import java.util.List;

public interface IInsuranceSurveyorRepository {
    void addInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor);
    void updateInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor);
    void deleteInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor);
    InsuranceSurveyor getInsuranceSurveyorById(int id);
    List<InsuranceSurveyor> getAllInsuranceSurveyors();
}
