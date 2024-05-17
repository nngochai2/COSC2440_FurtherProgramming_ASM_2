package org.nikisurance.service.impl;

import org.nikisurance.entity.InsuranceCard;
import org.nikisurance.service.interfaces.InsuranceCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceCardServiceImpl implements InsuranceCardService {

    @Override
    public InsuranceCard addInsuranceCard(InsuranceCard insuranceCard) {
        return null;
    }

    @Override
    public InsuranceCard getInsuranceCard(Long id) {
        return null;
    }

    @Override
    public List<InsuranceCard> getAllInsuranceCards() {
        return List.of();
    }

    @Override
    public void deleteInsuranceCard(Long id) {

    }
}
