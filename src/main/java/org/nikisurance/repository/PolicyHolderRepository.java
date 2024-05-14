package org.nikisurance.repository;

import org.nikisurance.entity.PolicyHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyHolderRepository extends JpaRepository<PolicyHolder, Long> {
}
