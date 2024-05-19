package org.nikisurance.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.nikisurance.entity.Dependent;
import org.nikisurance.entity.InsuranceCard;
import org.nikisurance.service.interfaces.DependentService;
import org.nikisurance.service.interfaces.InsuranceCardService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DependentServiceImpl extends EntityRepository implements DependentService {

    private final InsuranceCardService insuranceCardService;

    public DependentServiceImpl() {
        insuranceCardService = new InsuranceCardServiceImpl();
    }

    @Override
    public void addDependent(Dependent dependent) {
        performOperation(em -> {
            em.persist(dependent);
            createInsuranceCardForDependent(dependent);
        });
    }

    private void createInsuranceCardForDependent(Dependent dependent) {
        InsuranceCard insuranceCard = new InsuranceCard();
        insuranceCard.setCardHolder(dependent);
        insuranceCard.setIssuedDate(new Date());

        // Set the expiry date for one year from the issue date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(insuranceCard.getIssuedDate());
        calendar.add(Calendar.YEAR, 1);
        insuranceCard.setExpiryDate(calendar.getTime());

        insuranceCardService.addInsuranceCard(insuranceCard);
    }

    @Override
    public Dependent getDependent(Long id) {
        return performReturningOperation(em -> em.find(Dependent.class, id));
    }

    @Override
    public List<Dependent> getAllDependents() {
        return performReturningOperation(em -> em.createQuery("from Dependent", Dependent.class).getResultList());
    }

    @Override
    public void deleteDependent(Long id) {
        performOperation(em -> {
            Dependent dependent = em.find(Dependent.class, id);
            if (dependent != null) {
                em.remove(dependent);
            } else {
                throw new EntityNotFoundException("Dependent with id " + id + " not found");
            }
        });
    }

    @Override
    public void updateDependent(Dependent dependent) {
        performOperation(em -> em.merge(dependent));
    }

    @Override
    public List<Dependent> getDependentsByPolicyHolderId(Long policyHolderId) {
        return performReturningOperation(em ->
                em.createQuery("SELECT d FROM Dependent d WHERE d.policyHolder.id = :policyHolderId", Dependent.class)
                        .setParameter("policyHolderId", policyHolderId)
                        .getResultList()
        );
    }
}