package br.com.apihubinovacao.domain.coauthor;

import br.com.apihubinovacao.core.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

public record CoauthorDTO(

        @Null
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String email,

        @NotBlank
        String phone,

        Boolean enabled ,

        LocalDateTime createdDate,

        LocalDateTime lastModifiedDate


) implements BaseDTO {}