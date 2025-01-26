package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "PUBLISH")
public class Publish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "INTEGRATOR_PROJECT_idINTEGRATOR_PROJECT")
    private IntegratorProject integratorProject;

    @ManyToOne
    @JoinColumn(name = "STARTUP_idSTARTUP")
    private Startup startup;

    @ManyToOne
    @JoinColumn(name = "OPPORTUNITIES_BANK_idOPPORTUNITIES_BANK")
    private OpportunitiesBank opportunitiesBank;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IntegratorProject getIntegratorProject() {
        return integratorProject;
    }

    public void setIntegratorProject(IntegratorProject integratorProject) {
        this.integratorProject = integratorProject;
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
}
