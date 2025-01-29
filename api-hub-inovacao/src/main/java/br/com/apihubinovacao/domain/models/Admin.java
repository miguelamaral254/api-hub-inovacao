package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ADMIN")
public class Admin extends User implements UserBase {

    @Column(name = "cnpj", length = 20, nullable = false, unique = true)
    private String cnpj;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public List<Phone> getPhones() {
        return List.of();
    }

    
}