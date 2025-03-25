package br.com.apihubinovacao.domain.enterprise;

import br.com.apihubinovacao.core.BaseDTO;
import br.com.apihubinovacao.domain.address.AddressDTO;
import br.com.apihubinovacao.domain.users.Role;
import br.com.apihubinovacao.validations.CreateValidation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record EnterpriseDTO(

        @Null
        Long id,

        @NotBlank(groups = CreateValidation.class)
        String nomeEmpresa,

        @NotBlank(groups = CreateValidation.class)
        @Pattern(regexp = "\\d{14}", message = "CNPJ deve ter 14 dígitos")
        String cnpj,

        @NotBlank(groups = CreateValidation.class)
        String setorAtuacao,

        @NotBlank(groups = CreateValidation.class)
        @Email(message = "Email com formato inválido")
        String email,

        @NotBlank(groups = CreateValidation.class)
        String password,

        @NotBlank(groups = CreateValidation.class)
        String phone,

        @NotBlank(groups = CreateValidation.class)
        @Enumerated(EnumType.STRING)
        Role role,

        @NotBlank(groups = CreateValidation.class)
        String reprentantName,

        @NotBlank(groups = CreateValidation.class)
        String reprentantPosition,

        @NotBlank(groups = CreateValidation.class)
        String reprentantEmail,

        @NotBlank
        String reprentantPhone,

        @NotBlank(groups = CreateValidation.class)
        AddressDTO address,

        @Null
        Boolean enabled,

        @Null
        LocalDateTime createdDate,

        @Null
        LocalDateTime lastModifiedDate

) implements BaseDTO {}