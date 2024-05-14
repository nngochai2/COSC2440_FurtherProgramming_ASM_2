package org.nikisurance.service.impl;

import org.nikisurance.entity.Dependent;
import org.nikisurance.repository.DependentRepository;
import org.nikisurance.service.interfaces.DependentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DependentServiceImpl implements DependentService {

    private final DependentRepository dependentRepository;

    @Autowired
    public DependentServiceImpl(DependentRepository dependentRepository) {
        this.dependentRepository = dependentRepository;
    }

    @Override
    public Dependent addDependent(Dependent dependent) {
        return dependentRepository.save(dependent);
    }

    @Override
    public Dependent getDependent(Long id) {
        return dependentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Dependent> getAllDependents() {
        return dependentRepository.findAll();
    }

    @Override
    public void deleteDependent(Long id) {
        dependentRepository.deleteById(id);
    }
}
