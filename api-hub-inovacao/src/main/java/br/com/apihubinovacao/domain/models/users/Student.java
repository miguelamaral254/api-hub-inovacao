package br.com.apihubinovacao.domain.models.users;

import br.com.apihubinovacao.domain.models.projects.AcademicProject;
import br.com.apihubinovacao.domain.models.projects.Startup;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "STUDENT")
public class Student extends User implements UserBase {

    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String cpf;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Phone> phones; // Relacionamento com a tabela Phone

    @OneToMany(mappedBy = "student")
    private List<Startup> startups;

    @OneToMany(mappedBy = "student")
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