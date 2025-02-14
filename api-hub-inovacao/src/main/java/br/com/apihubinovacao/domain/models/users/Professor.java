package br.com.apihubinovacao.domain.models.users;

import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import br.com.apihubinovacao.domain.models.projects.Startup;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "PROFESSOR")
public class Professor extends User implements UserBase {

    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String cpf;

    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private List<Phone> phones;

    @OneToMany(mappedBy = "professor")
    private List<Startup> startups;

    @OneToMany(mappedBy = "professor")
    private List<AcademicProject> academicProjects;

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

    public List<Startup> getStartups() {
        return startups;
    }

    public void setStartups(List<Startup> startups) {
        this.startups = startups;
    }

    public List<AcademicProject> getAcademicProjects() {
        return academicProjects;
    }

    public void setAcademicProjects(List<AcademicProject> academicProjects) {
        this.academicProjects = academicProjects;
    }
}