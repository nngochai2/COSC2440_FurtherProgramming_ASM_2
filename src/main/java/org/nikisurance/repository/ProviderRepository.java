package org.nikisurance.repository;

import org.nikisurance.entity.Claim;
import org.nikisurance.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    @Query("SELECT COUNT(p) FROM Provider p WHERE TYPE(p) = :type")
    int countByRole(Class<? extends Provider> type);
}
