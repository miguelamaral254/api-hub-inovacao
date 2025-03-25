package br.com.apihubinovacao.domain.users;

import br.com.apihubinovacao.core.BaseDTO;
import br.com.apihubinovacao.domain.phone.Phone;
import br.com.apihubinovacao.validations.CreateValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public record UserDTO(

        @Null
        Long id,

        @NotBlank(groups = CreateValidation.class)
        String name,

        @NotBlank(groups = CreateValidation.class)
        String registration,

        @NotNull(groups = CreateValidation.class)
        @Enumerated(EnumType.STRING)
        Role role,

        List<Phone> phones,

        @NotBlank(groups = CreateValidation.class)
        @Email(message = "Email com formato inválido")
        String email,

        @NotBlank(groups = CreateValidation.class)
        String password,

        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
        String cpf,

        @Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 dígitos")
        String cnpj,

        @Null
        Boolean enabled,

        @Null
        LocalDateTime createdDate,

        @Null
        LocalDateTime lastModifiedDate

) implements BaseDTO {}