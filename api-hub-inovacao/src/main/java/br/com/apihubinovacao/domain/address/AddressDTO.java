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
        String city,

        @NotBlank
        String state,

        @NotBlank
        String zipCode,

        @NotBlank
        String country,

        @Null
        LocalDateTime createdDate,

        @Null
        LocalDateTime lastModifiedDate


) implements BaseDTO {
}