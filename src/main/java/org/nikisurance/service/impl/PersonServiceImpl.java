package org.nikisurance.service.impl;

import org.nikisurance.entity.Person;
import org.nikisurance.repository.PersonRepository;
import org.nikisurance.service.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person getPerson(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
