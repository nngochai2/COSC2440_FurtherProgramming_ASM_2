package org.nikisurance.controller;

import org.nikisurance.entity.Person;
import org.nikisurance.service.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons") // Base URL for all methods in this controller
public class PersonController {
    private final PersonService personService;

    @Autowired // Dependency injection of PersonService
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping // Handle HTTP POST request to /persons
    public ResponseEntity<Person> addPerson(@RequestBody Long id) {
        Person person = personService.getPerson(id);
        return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}") // Handles HTTP GET requests to /person/{id}
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        Person person = personService.getPerson(id);
        return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }

    @GetMapping // Handles HTTP GET requests to /persons
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        return ResponseEntity.ok(persons);
    }

    @DeleteMapping
    public ResponseEntity<Person> deletePerson(@RequestBody Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
