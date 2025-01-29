package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "PROFESSOR")
public class Professor extends User implements UserBase {

    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String cpf;

    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private List<Phone> phones;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public List<Phone> getPhones() {
        return phones != null ? phones : List.of();
    }
}