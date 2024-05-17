package org.nikisurance.service.impl;

import org.nikisurance.entity.Person;
import org.nikisurance.service.interfaces.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Override
    public Person addPerson(Person person) {
        return null;
    }

    @Override
    public Person getPerson(Long id) {
        return null;
    }

    @Override
    public List<Person> getAllPersons() {
        return List.of();
    }

    @Override
    public void deletePerson(Long id) {

    }
}
