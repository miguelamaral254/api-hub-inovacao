package br.com.apihubinovacao.domain.models.projects;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.models.users.Professor;
import br.com.apihubinovacao.domain.models.users.Student;
import jakarta.persistence.*;
import br.com.apihubinovacao.domain.enums.TypeAP;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ACADEMIC_PROJECT")
public class AcademicProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String urlPhoto;
    private String pdfLink;
    private String siteLink;

    @Enumerated(EnumType.STRING)
    private TypeAP typeAP;

    @Column(name = "author_email")
    private String authorEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSolicitation status;

    @OneToMany(mappedBy = "academicProject", fetch = FetchType.LAZY)
    private List<Coauthor> coauthors;

    @ManyToOne
    @JoinColumn(name = "professor_idUSER")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "student_idUSER")
    private Student student;

    private LocalDate creationDate;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }

    public String getSiteLink() {
        return siteLink;
    }

    public void setSiteLink(String siteLink) {
        this.siteLink = siteLink;
    }

    public TypeAP getTypeAP() {
        return typeAP;
    }

    public void setTypeAP(TypeAP typeAP) {
        this.typeAP = typeAP;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public StatusSolicitation getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitation status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<Coauthor> getCoauthors() {
        return coauthors;
    }

    public void setCoauthors(List<Coauthor> coauthors) {
        this.coauthors = coauthors;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}