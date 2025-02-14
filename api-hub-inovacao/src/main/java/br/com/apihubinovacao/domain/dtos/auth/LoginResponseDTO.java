package br.com.apihubinovacao.domain.dtos.auth;


public record LoginResponseDTO(String token, String email, String role, String message) {}
