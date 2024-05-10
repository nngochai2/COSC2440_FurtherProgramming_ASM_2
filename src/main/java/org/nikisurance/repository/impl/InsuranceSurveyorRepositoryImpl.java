package org.nikisurance.repository.impl;

import jakarta.persistence.TypedQuery;
import org.nikisurance.entity.InsuranceSurveyor;
import org.nikisurance.repository.EntityRepository;
import org.nikisurance.repository.repoInterface.IInsuranceSurveyorRepository;

import java.util.List;

public class InsuranceSurveyorRepositoryImpl extends EntityRepository implements IInsuranceSurveyorRepository {

    @Override
    public void addInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        em.getTransaction().begin();
        em.persist(insuranceSurveyor);
        em.getTransaction().commit();
    }

    @Override
    public void updateInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        em.getTransaction().begin();
        em.merge(insuranceSurveyor);
        em.getTransaction().commit();
    }

    @Override
    public void deleteInsuranceSurveyor(InsuranceSurveyor insuranceSurveyor) {
        em.getTransaction().begin();
        em.remove(insuranceSurveyor);
        em.getTransaction().commit();
    }

    @Override
    public InsuranceSurveyor getInsuranceSurveyorById(int id) {
        em.getTransaction().begin();
        InsuranceSurveyor insuranceSurveyor = em.find(InsuranceSurveyor.class, id);
        em.getTransaction().commit();
        return insuranceSurveyor;
    }

    @Override
    public List<InsuranceSurveyor> getAllInsuranceSurveyors() {
        em.getTransaction().begin();
        TypedQuery<InsuranceSurveyor> query = em.createQuery("SELECT i FROM InsuranceSurveyor i", InsuranceSurveyor.class);
        List<InsuranceSurveyor> insuranceSurveyors = query.getResultList();
        em.getTransaction().commit();
        return insuranceSurveyors;
    }
}
