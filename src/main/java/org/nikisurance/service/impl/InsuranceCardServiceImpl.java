package org.nikisurance.service.impl;

import org.nikisurance.entity.InsuranceCard;
import org.nikisurance.repository.InsuranceCardRepository;
import org.nikisurance.service.interfaces.InsuranceCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceCardServiceImpl implements InsuranceCardService {

    private final InsuranceCardRepository insuranceCardRepository;

    @Autowired
    public InsuranceCardServiceImpl(InsuranceCardRepository insuranceCardRepository) {
        this.insuranceCardRepository = insuranceCardRepository;
    }

    @Override
    public InsuranceCard addInsuranceCard(InsuranceCard insuranceCard) {
        return insuranceCardRepository.save(insuranceCard);
    }

    @Override
    public InsuranceCard getInsuranceCard(Long id) {
        return insuranceCardRepository.findById(id).orElse(null);
    }

    @Override
    public List<InsuranceCard> getAllInsuranceCards() {
        return insuranceCardRepository.findAll();
    }

    @Override
    public void deleteInsuranceCard(Long id) {
        insuranceCardRepository.deleteById(id);
    }
}
