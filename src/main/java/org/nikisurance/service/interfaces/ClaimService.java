package org.nikisurance.service.interfaces;

import org.nikisurance.entity.Claim;

import java.util.List;

public interface ClaimService {
    Claim addClaim(Claim claim);
    Claim getClaim(String id);
    List<Claim> getAllClaims();
    void deleteClaim(Claim claim);
}
