package br.com.apihubinovacao.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "PUBLISH")
public class Publish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPUBLISH")
    private Long id;






    @ManyToOne
    @JoinColumn(name = "INTEGRATOR_PROJECT_idINTEGRATOR_PROJECT")
    private IntegratorProject integratorProject;

    @ManyToOne
    @JoinColumn(name = "STARTUP_idSTARTUP")
    private Startup startup;

    @ManyToOne
    @JoinColumn(name = "OPPORTUNITIES_BANK_idOPPORTUNITIES_BANK")
    private Startup OportunitiesBank;
}
