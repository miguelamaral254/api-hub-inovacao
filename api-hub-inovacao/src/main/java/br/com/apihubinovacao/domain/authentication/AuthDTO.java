package br.com.apihubinovacao.domain.authentication;

public record AuthDTO(
       String token,
       String email,
       String role,
       String message
) {
}
