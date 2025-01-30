package br.com.apihubinovacao.domain.dtos.projects;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeAP;

import java.util.List;

public record AcademicProjectCreateProfessorDTO(
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        TypeAP typeAP,
        String userEmail,
        StatusSolicitation status,
        Long professorId,
        List<CoauthorDTO> coauthors
) {}