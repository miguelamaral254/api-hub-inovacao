package br.com.apihubinovacao.domain.models.users;

import br.com.apihubinovacao.domain.models.Publish;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "MANAGER")
public class Manager extends User implements UserBase {

    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String cpf;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Phone> phones;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Publish> publishes;

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