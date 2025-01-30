package br.com.apihubinovacao.domain.models.projects;

import jakarta.persistence.*;

@Entity
@Table(name = "COAUTHOR_SUBMISSION")
public class Coauthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @Column(name = "phone", length = 50)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "STARTUP_id")
    private Startup startup;

    @ManyToOne
    @JoinColumn(name = "ACADEMIC_PROJECT_id")
    private AcademicProject academicProject;

    // Construtor padrão (necessário para JPA)
    public Coauthor() {
    }

    // Construtor com parâmetros
    public Coauthor(String name, String email, String phone, Startup startup, AcademicProject academicProject) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.startup = startup;
        this.academicProject = academicProject;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Startup getStartup() {
        return startup;
    }

    public void setStartup(Startup startup) {
        this.startup = startup;
    }

    public AcademicProject getAcademicProject() {
        return academicProject;
    }

    public void setAcademicProject(AcademicProject academicProject) {
        this.academicProject = academicProject;
    }
}