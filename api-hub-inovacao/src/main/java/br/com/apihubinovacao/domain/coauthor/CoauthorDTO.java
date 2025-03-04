package br.com.apihubinovacao.domain.coauthor;

import br.com.apihubinovacao.core.BaseDTO;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CoauthorDTO(

        Long id,

        @NotBlank
        String name,

        @NotBlank
        String email,

        @NotBlank
        String phone,

        LocalDateTime createdDate,

        LocalDateTime lastModifiedDate

) implements BaseDTO {}