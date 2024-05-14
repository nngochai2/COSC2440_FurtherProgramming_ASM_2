package org.nikisurance.repository;

import org.nikisurance.entity.PolicyOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyOwnerRepository extends JpaRepository<PolicyOwner, Long> {
}
