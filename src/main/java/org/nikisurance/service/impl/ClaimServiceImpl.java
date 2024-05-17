package org.nikisurance.service.impl;

import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;
import org.nikisurance.service.interfaces.ClaimService;

import java.util.List;
public class ClaimServiceImpl implements ClaimService {

    @Override
    public Claim addClaim(Claim claim) {
        return null;
    }

    @Override
    public Claim getClaim(String id) {
        return null;
    }

    @Override
    public List<Claim> getAllClaims() {
        return List.of();
    }

    @Override
    public void deleteClaim(Claim claim) {

    }

    @Override
    public void updateClaim(Claim claim) {

    }

    @Override
    public long getCountByStatus(ClaimStatus status) {
        return 0;
    }
}
