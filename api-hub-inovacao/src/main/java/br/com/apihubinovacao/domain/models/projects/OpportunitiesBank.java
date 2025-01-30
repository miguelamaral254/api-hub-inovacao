package br.com.apihubinovacao.domain.models.projects;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.models.users.PartnerCompany;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "OPPORTUNITIES_BANK")
public class OpportunitiesBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 300, nullable = false)
    private String title;

    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "url_photo", length = 1000)
    private String urlPhoto;

    @Column(name = "siteLink", length = 200)
    private String site;

    @Column(name = "pdfLink")
    private String pdfLink;

    @Column(name = "flag_active")
    private boolean flagActive;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusSolicitation status;

    @Column(name = "author_email")
    private String authorEmail;  // Agora armazenamos o e-mail do usuário diretamente

    @ManyToOne
    @JoinColumn(name = "partnerCompany_idUSER", nullable = false)
    private PartnerCompany partnerCompany;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "validation_date")
    private LocalDate validationDate;


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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
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

    public LocalDate getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(LocalDate validationDate) {
        this.validationDate = validationDate;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }


    public PartnerCompany getPartnerCompany() {
        return partnerCompany;
    }

    public void setPartnerCompany(PartnerCompany partnerCompany) {
        this.partnerCompany = partnerCompany;
    }
}
