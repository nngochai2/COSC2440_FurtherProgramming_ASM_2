package org.nikisurance.service.impl;

import org.nikisurance.entity.Provider;
import org.nikisurance.service.interfaces.ProviderService;

import java.util.List;

public class ProviderServiceImpl implements ProviderService {

    @Override
    public Provider addProvider(Provider provider) {
        return null;
    }

    @Override
    public Provider getProvider(Long id) {
        return null;
    }

    @Override
    public List<Provider> getAllProviders() {
        return List.of();
    }

    @Override
    public void deleteProvider(Long id) {

    }

    @Override
    public int countInsuranceManagers() {
        return 0;
    }

    @Override
    public int countInsuranceProvider() {
        return 0;
    }
}
