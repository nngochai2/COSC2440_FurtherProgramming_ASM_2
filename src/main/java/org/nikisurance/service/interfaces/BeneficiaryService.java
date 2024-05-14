package org.nikisurance.service.interfaces;

import org.nikisurance.entity.Beneficiary;

import java.util.List;

public interface BeneficiaryService {
    Beneficiary addBeneficiary(Beneficiary b);
    Beneficiary getBeneficiary(Long id);
    List<Beneficiary> getAllBeneficiaries();
    void deleteBeneficiary(Long id);
}
