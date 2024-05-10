package org.nikisurance.repository.repoInterface;

import org.nikisurance.entity.InsuranceCard;

import java.util.List;

public interface IInsuranceCardRepository {
    void addInsuranceCard(InsuranceCard card);
    InsuranceCard getInsuranceCard(int id);
    List<InsuranceCard> getAllInsuranceCards();
    void updateInsuranceCard(InsuranceCard card);
    void deleteInsuranceCard(int id);
}
