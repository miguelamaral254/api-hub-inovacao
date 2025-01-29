package br.com.apihubinovacao.domain.dtos;

import br.com.apihubinovacao.domain.enums.Role;
import java.util.List;

public record UserCreateCpfDTO(
        String name,
        String email,
        String password,
        String registration,
        Role role,
        String institutionOrganization,
        boolean userStatus,
        String cpf,
        List<PhoneCreateDTO> phones
) {}