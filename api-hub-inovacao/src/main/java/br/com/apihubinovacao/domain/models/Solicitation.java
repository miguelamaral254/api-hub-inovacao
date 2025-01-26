package br.com.apihubinovacao.domain.models;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "SOLICITATION")
public class Solicitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSOLICITATION")
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
    private Startup OportunitiesBank;

    @ManyToOne
    @JoinColumn(name = "INTEGRATOR_PROJECT_idINTEGRATOR_PROJECT")
    private IntegratorProject integratorProject;




}
