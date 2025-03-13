package br.com.apihubinovacao.domain.models.projects;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.models.users.PartnerCompany;
import br.com.apihubinovacao.domain.models.users.Phone;
import br.com.apihubinovacao.domain.models.users.Professor;
import br.com.apihubinovacao.domain.models.users.Student;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "STARTUP")
public class Startup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 300, nullable = false)
    private String title;

    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "url_photo", length = 1000)
    private String urlPhoto;

    @Column(name = "siteLink", length = 200)
    private String siteLink;

    @Column(name = "pdfLink")
    private String pdfLink;

    @Column(name = "flag_active")
    private boolean flagActive;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusSolicitation status;

    @Column(name = "author_email")
    private String authorEmail;

    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Coauthor> coauthors;


    @ManyToOne
    @JoinColumn(name = "professor_idUSER")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "student_idUSER")
    private Student student;

    @Column(name = "CNPJ", length = 18)
    private String cnpj;

    @Column(name = "feedback", nullable = true)
    private String feedback;

    @Column(name = "justification", nullable = true)
    private String justification;

    @Column(name = "id_manager", nullable = true)
    private Long idManager;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Long getIdManager() {
        return idManager;
    }

    public void setIdManager(Long idManager) {
        this.idManager = idManager;
    }

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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getSiteLink() {
        return siteLink;
    }

    public void setSiteLink(String siteLink) {
        this.siteLink = siteLink;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink;
    }

    public boolean isFlagActive() {
        return flagActive;
    }

    public void setFlagActive(boolean flagActive) {
        this.flagActive = flagActive;
    }

    public StatusSolicitation getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitation status) {
        this.status = status;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
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
