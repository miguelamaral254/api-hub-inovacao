package br.com.apihubinovacao.domain.startup;
import br.com.apihubinovacao.core.BaseDTO;
import br.com.apihubinovacao.validations.CreateValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record StartupDTO(
        @Null
        Long id,

        @NotBlank(groups = CreateValidation.class)
        String title,

        @NotBlank(groups = CreateValidation.class)
        String description,

        @Null
        Boolean enabled,

        @NotBlank(groups = CreateValidation.class)
        Long userId,

        @Nullable
        String cnpj,

        @Nullable
        Long managerId,

        @Nullable
        String feedback,

        @Nullable
        String justification,

        @Null
        LocalDateTime createdDate,

        @Null
        LocalDateTime lastModifiedDate

) implements BaseDTO {
}