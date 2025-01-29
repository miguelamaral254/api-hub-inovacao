package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "STUDENT")
public class Student extends User implements UserBase {

    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String cpf;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Phone> phones; // Relacionamento com a tabela Phone

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public List<Phone> getPhones() {
        return phones != null ? phones : List.of();  // Agora retorna os telefones associados ao estudante
    }

}