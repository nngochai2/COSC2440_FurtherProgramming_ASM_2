package org.nikisurance.repository.repoInterface;

import org.nikisurance.entity.Beneficiary;

import java.util.List;

public interface IBeneficiaryRepository {
    void addBeneficiary(Beneficiary beneficiary);
    Beneficiary getBeneficiary(int id);
    List<Beneficiary> getBeneficiaries();
    void deleteBeneficiary(int id);
}
