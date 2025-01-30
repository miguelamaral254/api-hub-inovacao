package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;
import br.com.apihubinovacao.domain.enums.TypeAP;

import java.time.LocalDate;

@Entity
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
    private String authorEmail;  // Agora armazenamos o e-mail do usuário diretamente

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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}