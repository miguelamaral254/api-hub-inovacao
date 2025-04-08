package br.com.apihubinovacao.domain.editals;

import br.com.apihubinovacao.core.BaseDTO;
import br.com.apihubinovacao.domain.users.User;
import br.com.apihubinovacao.validations.CreateValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record EditalDTO (

        @Null
        Long id,

         @NotBlank(groups = CreateValidation.class)
         String title,

         String description,

         String acessLink,

         LocalDate initialDate,

         LocalDate finalDate,

         Long idUser,

        @Null
         Boolean enabled ,

        @Null
         LocalDateTime createdDate,

        @Null
         LocalDateTime lastModifiedDate
)
implements BaseDTO {
}
