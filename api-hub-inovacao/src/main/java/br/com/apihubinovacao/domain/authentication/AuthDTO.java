package br.com.apihubinovacao.domain.authentication;

public record AuthDTO(
        Long idUser,
        String token,
        String email,
        String role,
        String message
) {
}