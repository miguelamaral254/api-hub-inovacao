package br.com.apihubinovacao.domain.dtos.user;

import br.com.apihubinovacao.domain.enums.Role;

public record UserUpdateDTO(
        String name,
        String email,
        String password,
        String registration,
        Role role,
        String institutionOrganization,
        boolean userStatus
) {}
