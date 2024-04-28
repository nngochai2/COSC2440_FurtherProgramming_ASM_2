package org.nikisurance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class InsuranceManager implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private int id;

    private String name;
    private String password;

    public InsuranceManager(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public InsuranceManager() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
