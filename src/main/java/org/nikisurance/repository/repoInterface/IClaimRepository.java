package org.nikisurance.repository.repoInterface;

import org.nikisurance.entity.Claim;

import java.util.List;

public interface IClaimRepository {
    Claim getClaimById(int id);
    void saveClaim(Claim claim);
    void deleteClaim(Claim claim);
    List<Claim> getAllClaims();
    List<Claim> getClaimsByCustomer(int customerId);
    List<Claim> getClaimsFromDateToDate(int fromDate, int toDate);
}
