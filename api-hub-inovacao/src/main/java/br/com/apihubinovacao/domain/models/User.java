package br.com.apihubinovacao.domain.models;

import br.com.apihubinovacao.domain.enums.Role;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class User implements UserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "registration", length = 45)
    private String registration;

    @Column(name = "institution_organization", length = 100)
    private String institutionOrganization;

    @Column(name = "userStatus", nullable = false)
    private boolean userStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 50, nullable = false)
    private Role role;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRegistration() {
        return registration;
    }

    public String getInstitutionOrganization() {
        return institutionOrganization;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public Role getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setInstitutionOrganization(String institutionOrganization) {
        this.institutionOrganization = institutionOrganization;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}