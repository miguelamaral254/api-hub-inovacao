package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "INTEGRATOR_PROJECT")
public class IntegratorProject extends Abstractproject {


    @OneToMany(mappedBy = "integratorProject")
    private List<Solicitation> submissions;
}
