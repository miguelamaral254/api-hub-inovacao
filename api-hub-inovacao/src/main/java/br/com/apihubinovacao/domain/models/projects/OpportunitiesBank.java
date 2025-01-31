package br.com.apihubinovacao.domain.models.projects;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeBO;
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

    private String title;
    private String description;
    private String urlPhoto;
    private String pdfLink;
    private String siteLink;

    public TypeBO getTypeBO() {
        return typeBO;
    }

    public void setTypeBO(TypeBO typeBO) {
        this.typeBO = typeBO;
    }

    private TypeBO typeBO;

    @Column(name = "author_email")
    private String authorEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSolicitation status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "flag_active")
    private boolean flagActive;

    @ManyToOne
    @JoinColumn(name = "partnerCompany_id", nullable = false)
    private PartnerCompany partnerCompany;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "validation_date")
    private LocalDate validationDate;

    @Column(name = "feedback", nullable = true)
    private String feedback;

    @Column(name = "justification", nullable = true)
    private String justification;

    @Column(name = "id_manager", nullable = true)
    private Long idManager;

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

    public boolean isFlagActive() {
        return flagActive;
    }

    public void setFlagActive(boolean flagActive) {
        this.flagActive = flagActive;
    }

    public PartnerCompany getPartnerCompany() {
        return partnerCompany;
    }

    public void setPartnerCompany(PartnerCompany partnerCompany) {
        this.partnerCompany = partnerCompany;
    }

    public LocalDate getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(LocalDate validationDate) {
        this.validationDate = validationDate;
    }

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
}