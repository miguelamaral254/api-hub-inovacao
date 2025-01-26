package br.com.apihubinovacao.domain.models;


import jakarta.persistence.*;

@Entity
@Table(name = "AWARDS")
public class Awards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAWARDS")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "STARTUP_idSTARTUP")
    private Startup startup;

    @ManyToOne
    @JoinColumn(name = "INTEGRATOR_PROJECT_idINTEGRATOR_PROJECT")
    private IntegratorProject integratorProject;
}
