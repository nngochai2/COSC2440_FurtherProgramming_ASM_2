package org.nikisurance.repository;

import org.nikisurance.entity.InsuranceCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceCardRepository extends JpaRepository<InsuranceCard, Long> {
}
