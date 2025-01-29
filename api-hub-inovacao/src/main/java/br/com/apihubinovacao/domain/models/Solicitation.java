package br.com.apihubinovacao.domain.models;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "SOLICITATION")
public class Solicitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    private StatusSolicitation status;

    @Column(name = "solicition_date", nullable = false)
    private LocalDate solicitionDate;

    @Column(name = "description_solicition")
    private String descriptionSolicition;

    @OneToOne
    @JoinColumn(name = "SUBMISSION_idSUBMISSION")
    private Submission submission;

    @ManyToOne
    @JoinColumn(name = "STARTUP_idSTARTUP")
    private Startup startup;

    @ManyToOne
    @JoinColumn(name = "OPPORTUNITIES_BANK_idOPPORTUNITIES_BANK")
    private OpportunitiesBank opportunitiesBank;

    @ManyToOne
    @JoinColumn(name = "INTEGRATOR_PROJECT_idINTEGRATOR_PROJECT")
    private IntegratorProject integratorProject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusSolicitation getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitation status) {
        this.status = status;
    }

    public LocalDate getSolicitionDate() {
        return solicitionDate;
    }

    public void setSolicitionDate(LocalDate solicitionDate) {
        this.solicitionDate = solicitionDate;
    }

    public String getDescriptionSolicition() {
        return descriptionSolicition;
    }

    public void setDescriptionSolicition(String descriptionSolicition) {
        this.descriptionSolicition = descriptionSolicition;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public Startup getStartup() {
        return startup;
    }

    public void setStartup(Startup startup) {
        this.startup = startup;
    }

    public OpportunitiesBank getOpportunitiesBank() {
        return opportunitiesBank;
    }

    public void setOpportunitiesBank(OpportunitiesBank opportunitiesBank) {
        this.opportunitiesBank = opportunitiesBank;
    }

    public IntegratorProject getIntegratorProject() {
        return integratorProject;
    }

    public void setIntegratorProject(IntegratorProject integratorProject) {
        this.integratorProject = integratorProject;
    }
}
