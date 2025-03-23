package br.com.apihubinovacao.domain.enterprise;

import br.com.apihubinovacao.core.BaseDTO;
import br.com.apihubinovacao.domain.address.AddressDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

import java.time.LocalDateTime;

public record EnterpriseDTO(

        @Null
        Long id,

        @NotBlank
        String nomeEmpresa,

        @NotBlank
        String cnpj,

        @NotBlank
        String setorAtuacao,

        String phone,

        @NotBlank
        String reprentantName,

        @NotBlank
        String reprentantPosition,

        @NotBlank
        String reprentantEmail,

        @NotBlank
        String reprentantPhone,


        AddressDTO address,

        @Null
        Boolean enabled,

        @Null
        LocalDateTime createdDate,

        @Null
        LocalDateTime lastModifiedDate

) implements BaseDTO {}