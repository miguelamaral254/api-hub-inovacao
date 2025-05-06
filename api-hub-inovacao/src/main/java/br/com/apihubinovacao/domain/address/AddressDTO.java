package br.com.apihubinovacao.domain.address;

import br.com.apihubinovacao.core.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

public record AddressDTO(

        @Null
        Long id,

        @NotBlank
        String street,

        @NotBlank
        int number,

        @NotBlank
        String complement,

        @NotBlank
        String city,

        @NotBlank
        String state,

        @NotBlank
        String zipCode,

        @NotBlank
        String country,

        @Null
        Boolean enabled,

        @Null
        LocalDateTime createdDate,

        @Null
        LocalDateTime lastModifiedDate


) implements BaseDTO {
}