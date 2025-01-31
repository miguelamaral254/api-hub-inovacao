package br.com.apihubinovacao.domain.dtos.projects;

public record UpdateAcademicProjectDetailsDTO(
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink
) {}