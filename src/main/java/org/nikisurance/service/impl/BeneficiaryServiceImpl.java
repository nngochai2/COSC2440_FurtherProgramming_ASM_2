package org.nikisurance.service.impl;

import org.nikisurance.entity.Beneficiary;
import org.nikisurance.repository.BeneficiaryRepository;
import org.nikisurance.service.interfaces.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Override
    public Beneficiary addBeneficiary(Beneficiary b) {
        return beneficiaryRepository.save(b);
    }

    @Override
    public Beneficiary getBeneficiary(Long id) {
        return beneficiaryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Beneficiary> getAllBeneficiaries() {
        return beneficiaryRepository.findAll();
    }

    @Override
    public void deleteBeneficiary(Long id) {
        beneficiaryRepository.deleteById(id);
    }
}
