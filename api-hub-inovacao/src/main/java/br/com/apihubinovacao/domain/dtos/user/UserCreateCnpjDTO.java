package br.com.apihubinovacao.domain.dtos.user;

import br.com.apihubinovacao.domain.dtos.phone.PhoneCreateDTO;
import br.com.apihubinovacao.domain.enums.Role;
import java.util.List;

public record UserCreateCnpjDTO(
        String name,
        String email,
        String password,
        String registration,
        Role role,
        String institutionOrganization,
        boolean userStatus,
        String cnpj,
        List<PhoneCreateDTO> phones
) {}