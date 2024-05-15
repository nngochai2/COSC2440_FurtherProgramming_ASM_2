package org.nikisurance.controller;

import org.nikisurance.entity.Person;

public class UserSession {
    private static UserSession instance;
    private Person loggedInPerson;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public Person getLoggedInPerson() {
        return loggedInPerson;
    }

    public void setLoggedInPerson(Person loggedInPerson) {
        this.loggedInPerson = loggedInPerson;
    }
}
