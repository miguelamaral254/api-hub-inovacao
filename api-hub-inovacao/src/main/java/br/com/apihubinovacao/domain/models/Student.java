package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "STUDENT")
public class Student extends User implements UserBase {

    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public List<Phone> getPhones() {
        return List.of();
    }
}