package org.nikisurance.repository;

import org.nikisurance.entity.Claim;
import org.nikisurance.entity.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, String> {
    long countByStatus(ClaimStatus status);
}
