package br.com.apihubinovacao.domain.dtos.projects;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;

import java.util.List;

public record UpdateAcademicProjectDetailsDTO(
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        List<CoauthorDTO> coauthors
) {}