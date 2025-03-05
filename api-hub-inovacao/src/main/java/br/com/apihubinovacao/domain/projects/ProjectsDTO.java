package br.com.apihubinovacao.domain.projects;

import br.com.apihubinovacao.core.BaseDTO;
import br.com.apihubinovacao.domain.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.enums.ProjectType;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.validations.CreateValidation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectsDTO (
        Long id,

        @NotBlank(groups = CreateValidation.class)
        String title,

        @NotBlank(groups = CreateValidation.class)
        String description,

        @NotBlank(groups = CreateValidation.class)
        String urlPhoto,

        @NotBlank(groups = CreateValidation.class)
        String pdfLink,

        @NotBlank(groups = CreateValidation.class)
        String siteLink,

        @NotBlank(groups = CreateValidation.class)
        @Enumerated(EnumType.STRING)
        ProjectType projectType,

        @NotBlank(groups = CreateValidation.class)
        @Enumerated(EnumType.STRING)
        StatusSolicitation status,

        @NotBlank(groups = CreateValidation.class)
        Long idUser,

        @NotBlank(groups = CreateValidation.class)
        Long idManager,

        @NotBlank(groups = CreateValidation.class)
        String feedback,

        @NotBlank(groups = CreateValidation.class)
        String justification,

        Boolean enabled ,

        LocalDateTime createdDate,

        LocalDateTime lastModifiedDate,

        @Nullable
        @Valid
        List<CoauthorDTO> coauthors

) implements BaseDTO {
}