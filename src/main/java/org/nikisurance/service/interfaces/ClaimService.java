package org.nikisurance.service.interfaces;

import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;

import java.util.List;

public interface ClaimService {
    Claim addClaim(Claim claim);
    Claim getClaim(String id);
    List<Claim> getAllClaims();
    void deleteClaim(Claim claim);
    void updateClaim(Claim claim);
    long getCountByStatus(ClaimStatus status); // Updated method
}
