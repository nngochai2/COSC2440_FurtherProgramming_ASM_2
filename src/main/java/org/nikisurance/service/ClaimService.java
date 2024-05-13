package org.nikisurance.service;

import org.nikisurance.entity.Claim;

public class ClaimService {
    public Claim.Builder makeClaim() {
        return new Claim.Builder();
    }
}
