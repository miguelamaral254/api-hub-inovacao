package br.com.apihubinovacao.domain.dtos.user;

import br.com.apihubinovacao.domain.dtos.phone.PhoneResponseDTO;
import br.com.apihubinovacao.domain.enums.Role;
import java.util.List;

public record UserResponseCpfDTO(
        Long id,
        String name,
        String email,
        String registration,
        Role role,
        String institutionOrganization,
        boolean userStatus,
        String cpf,
        List<PhoneResponseDTO> phones
) { }