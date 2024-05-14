package org.nikisurance.service.interfaces;

import org.nikisurance.entity.InsuranceCard;

import java.util.List;

public interface InsuranceCardService {
    InsuranceCard addInsuranceCard(InsuranceCard insuranceCard);
    InsuranceCard getInsuranceCard(Long id);
    List<InsuranceCard> getAllInsuranceCards();
    void deleteInsuranceCard(Long id);
}
