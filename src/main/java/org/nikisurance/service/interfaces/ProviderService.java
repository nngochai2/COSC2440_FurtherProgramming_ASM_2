package org.nikisurance.service.interfaces;

import org.nikisurance.entity.Provider;

import java.util.List;

public interface ProviderService {
    void addProvider(Provider provider);
    Provider getProvider(Long id);
    List<Provider> getAllProviders();
    void deleteProvider(Long id);
    int countInsuranceManagers();
    int countInsuranceProvider();
}
