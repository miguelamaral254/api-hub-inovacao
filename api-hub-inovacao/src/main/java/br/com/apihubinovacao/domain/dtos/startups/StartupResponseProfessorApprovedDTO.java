package br.com.apihubinovacao.domain.dtos.startups;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;

import java.util.List;

public record StartupResponseProfessorApprovedDTO(
        Long id,
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        String cnpj,
        String currentUserEmail,
        String creationDate,
        Long professorId,
        String professorName,
        List<CoauthorDTO> coauthors
) {}
