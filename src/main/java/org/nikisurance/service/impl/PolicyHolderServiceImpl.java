package org.nikisurance.service.impl;

import org.nikisurance.entity.InsuranceCard;
import org.nikisurance.entity.PolicyHolder;
import org.nikisurance.service.interfaces.InsuranceCardService;
import org.nikisurance.service.interfaces.PolicyHolderService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import jakarta.persistence.TypedQuery;

public class PolicyHolderServiceImpl extends EntityRepository implements PolicyHolderService {

    private final InsuranceCardService insuranceCardService;

    public PolicyHolderServiceImpl() {
        insuranceCardService = new InsuranceCardServiceImpl();
    }
    @Override
    public void addPolicyHolder(PolicyHolder policyHolder) {
        performOperation(em -> {
            em.persist(policyHolder);
            createInsuranceCardForPolicyHolder(policyHolder);
        });
    }

    private void createInsuranceCardForPolicyHolder(PolicyHolder policyHolder) {
        InsuranceCard insuranceCard = new InsuranceCard();
        insuranceCard.setCardHolder(policyHolder);
        insuranceCard.setIssuedDate(new Date());

        // Set the expiry date for one year from the issue date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(insuranceCard.getIssuedDate());
        calendar.add(Calendar.YEAR, 1);
        insuranceCard.setExpiryDate(calendar.getTime());

        insuranceCardService.addInsuranceCard(insuranceCard);
    }

    @Override
    public PolicyHolder getPolicyHolder(Long id) {
        return performReturningOperation(em -> em.find(PolicyHolder.class, id));
    }

    @Override
    public List<PolicyHolder> getAllPolicyHolders() {
        return performReturningOperation(em -> em.createQuery("from PolicyHolder", PolicyHolder.class).getResultList());
    }

    @Override
    public void deletePolicyHolder(Long id) {
        performOperation(em -> {
            PolicyHolder policyHolder = em.find(PolicyHolder.class, id);
            if (policyHolder != null) {
                em.remove(policyHolder);
            } else {
                throw new IllegalArgumentException("PolicyHolder with id " + id + " not found");
            }
        });
    }

    @Override
    public void updatePolicyHolder(PolicyHolder policyHolder) {
        performOperation(em -> em.merge(policyHolder));
    }
}