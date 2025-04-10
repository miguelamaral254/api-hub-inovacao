package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.core.BaseDTO;
import br.com.apihubinovacao.domain.coauthor.CoauthorDTO;
import br.com.apihubinovacao.core.StatusSolicitation;
import br.com.apihubinovacao.validations.CreateValidation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectsDTO(
        @Null
        Long id,

        @Nullable
        String title,

        @Nullable
        String description,

        @Nullable
        String urlPhoto,

        @Nullable
        String pdfLink,

        @Nullable
        String siteLink,

        @Nullable
        String thematicArea,

        @Nullable
        String course,

        @Nullable
        String problem,

        @Nullable
        String generalObjective,

        @Nullable
        String specificObjective,

        @Nullable
        String expectedResults,

        @Nullable
        ProjectType projectType,

        @Nullable
        StatusSolicitation status,

        @Nullable
        Long idUser,

        @Nullable
        Long idManager,

        @Nullable
        String feedback,

        @Nullable
        String justification,

        @Null
        Boolean enabled,

        @Null
        LocalDateTime createdDate,

        @Null
        LocalDateTime lastModifiedDate,

        @Nullable
        @Valid
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonSetter(nulls = Nulls.AS_EMPTY)
        List<CoauthorDTO> coauthors


) implements BaseDTO {
}
