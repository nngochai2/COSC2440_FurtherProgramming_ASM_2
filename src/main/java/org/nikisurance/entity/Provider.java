package org.nikisurance.entity;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_role")
@Table(name = "provider")
public class Provider extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "person_role", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private AdminRole role;

    public Provider() {}

    public Provider(Long id, String name, String password, AdminRole role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public AdminRole getRole() {
        return role;
    }

    public void setRole(AdminRole role) {
        this.role = role;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
