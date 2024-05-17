package org.nikisurance.service.impl;

import org.nikisurance.entity.InsuranceManager;
import org.nikisurance.entity.InsuranceSurveyor;
import org.nikisurance.entity.Provider;
import org.nikisurance.repository.ProviderRepository;
import org.nikisurance.service.interfaces.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public Provider addProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public Provider getProvider(Long id) {
        return providerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    @Override
    public void deleteProvider(Long id) {
        providerRepository.deleteById(id);
    }

    @Override
    public int countInsuranceManagers() {
        return providerRepository.countByRole(InsuranceManager.class);
    }

    @Override
    public int countInsuranceProvider() {
        return providerRepository.countByRole(InsuranceSurveyor.class);
    }
}
