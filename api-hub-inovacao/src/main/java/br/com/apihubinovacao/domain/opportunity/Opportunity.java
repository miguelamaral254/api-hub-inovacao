package br.com.apihubinovacao.domain.opportunity;

import br.com.apihubinovacao.core.BaseEntity;
import br.com.apihubinovacao.core.StatusSolicitation;
import br.com.apihubinovacao.domain.enterprise.Enterprise;
import br.com.apihubinovacao.domain.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_opportunities")
public class Opportunity extends BaseEntity {

    private String urlPhoto;

    @NotBlank
    private String tituloDesafio;

    @NotBlank
    private String areaProblema;

    @NotBlank
    @Lob
    private String descricaoProblema;

    @NotBlank
    @Lob
    private String impactoProblema;

    @Lob
    private String solucoesTestadas;

    @NotBlank
    @Lob
    private String expectativas;

    @NotBlank
    @Lob
    private String restricoes;

    @NotBlank
    @Lob
    private String disponibilidadeDados;

    @NotBlank
    @Lob
    private boolean mentoriaSuporte;

    @NotBlank
    private boolean visitasTecnicas;

    @NotBlank
    @Lob
    private List<String> recursosDisponiveis;

    @NotBlank
    private boolean autorizacao;

    @Column(name ="type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OpportunityType opportunityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", nullable = false)
    private Enterprise enterprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_manager", nullable = true)
    private User idManager;

    @Column(name = "feedback", nullable = true)
    private String feedback;

    @Column(name = "justification", nullable = true)
    private String justification;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSolicitation status;
}
