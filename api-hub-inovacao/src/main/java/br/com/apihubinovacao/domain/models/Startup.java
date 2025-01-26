package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "STARTUP")
public class Startup extends AbstractProject {


    @Column(name = "CNPJ", length = 14)
    private String cnpj;


    @OneToMany(mappedBy = "startup")
    private List<Solicitation> submissions;

}
