package br.com.apihubinovacao.domain.dtos.projects;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.enums.TypeAP;

import java.util.List;

public record AcademicProjectResponseProfessorApprovedDTO(
        Long id,
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        TypeAP typeAP,
        String currentUserEmail,
        String creationDate,
        Long professorId,
        String professorName,
        List<CoauthorDTO> coauthors
){}