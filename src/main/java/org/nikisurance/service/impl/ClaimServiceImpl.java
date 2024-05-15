package org.nikisurance.service.impl;

import org.nikisurance.entity.Claim;
import org.nikisurance.repository.ClaimRepository;
import org.nikisurance.service.interfaces.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    @Autowired
    public ClaimServiceImpl(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    @Override
    public Claim addClaim(Claim claim) {
        return claimRepository.save(claim);
    }

    @Override
    public Claim getClaim(String id) {
        return claimRepository.findById(id).orElse(null);
    }

    @Override
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    @Override
    public void deleteClaim(Claim claim) {
        claimRepository.delete(claim);
    }
    public Claim updateClaim(Claim claim) {
        return claimRepository.save(claim);
    }
}
