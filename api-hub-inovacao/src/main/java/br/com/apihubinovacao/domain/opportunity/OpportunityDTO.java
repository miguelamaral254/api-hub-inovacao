package br.com.apihubinovacao.domain.opportunity;

import br.com.apihubinovacao.core.BaseDTO;
import br.com.apihubinovacao.core.StatusSolicitation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public record OpportunityDTO(
        @Null
        Long id,

        @Null
        Boolean enabled,

        @Null
        LocalDateTime createdDate,

        @Null
        LocalDateTime lastModifiedDate,

        @NotBlank
        String urlPhoto,

        @NotNull
        String tituloDesafio,

        @NotBlank
        String areaProblema,

        @NotBlank
        String descricaoProblema,

        @NotBlank
        String impactoProblema,

        String solucoesTestadas,

        @NotBlank
        String expectativas,

        @NotBlank
        String restricoes,

        @NotBlank
        String disponibilidadeDados,

        @NotBlank
        Boolean mentoriaSuporte,

        @NotNull
        Boolean visitasTecnicas,

        @NotNull
        List<String> recursosDisponiveis,

        @NotNull
        Boolean autorizacao,

        @NotNull
        OpportunityType opportunityType,

        @NotNull
        Long enterpriseId,

        @Nullable
        Long managerId,

        @Nullable
        String feedback,

        @Nullable
        String justification,

        @NotBlank
        StatusSolicitation status

) implements BaseDTO {
}