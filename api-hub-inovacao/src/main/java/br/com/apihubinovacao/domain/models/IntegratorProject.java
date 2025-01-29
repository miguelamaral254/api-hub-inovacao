package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "INTEGRATOR_PROJECT")
public class IntegratorProject extends AbstractProject {

    @OneToMany(mappedBy = "integratorProject")
    private List<Solicitation> submissions;


    public List<Solicitation> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Solicitation> submissions) {
        this.submissions = submissions;
    }
}