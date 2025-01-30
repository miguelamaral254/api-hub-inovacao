package br.com.apihubinovacao.domain.dtos;

import br.com.apihubinovacao.domain.enums.TypeAP;

public record AcademicProjectResponseDTO(
        Long id,
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        TypeAP typeAP,
        String currentUserEmail,
        String creationDate
) {}