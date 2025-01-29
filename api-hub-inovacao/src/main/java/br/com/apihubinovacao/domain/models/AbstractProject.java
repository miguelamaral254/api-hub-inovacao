package br.com.apihubinovacao.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@MappedSuperclass
public class AbstractProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 300, nullable = false)
    private String name;

    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "url_photo", length = 1000)
    private String urlPhoto;

    @Column(name = "responsible_institution", length = 200, nullable = false)
    private String responsibleInstitution;

    @Column(name = "site", length = 200)
    private String site;

    @Column(name = "status", length = 100)
    private String status;

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

    public String getResponsibleInstitution() {
        return responsibleInstitution;
    }

    public void setResponsibleInstitution(String responsibleInstitution) {
        this.responsibleInstitution = responsibleInstitution;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}