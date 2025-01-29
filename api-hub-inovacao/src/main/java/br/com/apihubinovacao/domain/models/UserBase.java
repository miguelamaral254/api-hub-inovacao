package br.com.apihubinovacao.domain.models;

import br.com.apihubinovacao.domain.enums.Role;

import java.util.List;

public interface UserBase {
    Long getId();
    String getName();
    String getEmail();
    String getRegistration();
    Role getRole();
    String getInstitutionOrganization();
    boolean isUserStatus();
    List<Phone> getPhones();
    String getPassword();

}