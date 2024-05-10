package org.nikisurance.controller;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.nikisurance.entity.User;
import org.nikisurance.repository.EntityRepository;

public class LoginController extends EntityRepository {
    public <T extends User> T login(Class<T> userType, String username, String password) {
        try {
            // Query to find the user by username
            String queryString = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            TypedQuery<T> query = em.createQuery(queryString, userType);
            query.setParameter("username", username);
            T user = query.getSingleResult();

            // Check if the retrieved user has the correct password
            if (user != null && user.checkPassword(password)) {
                System.out.println("Logged in successfully");
                return user;
            } else {
                System.err.println("Login failed");
                return null;
            }
        } catch (NoResultException e) {
            System.out.println("No user found");
            return null;
        }
    }
}
