package org.nikisurance.service.interfaces;

import org.nikisurance.entity.Person;

import java.util.List;

public interface PersonService {
    void addPerson(Person person);
    Person getPerson(Long id);
    List<Person> getAllPersons();
    void deletePerson(Long id);
}
