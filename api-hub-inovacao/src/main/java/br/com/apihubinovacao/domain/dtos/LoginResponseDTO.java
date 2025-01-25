package br.com.apihubinovacao.domain.dtos;


public record LoginResponseDTO(String token, String email, String role, String message) {}
