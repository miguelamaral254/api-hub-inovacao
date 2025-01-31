package br.com.apihubinovacao.domain.dtos.projects;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeAP;

public record AcademicProjectResponseStudentDTO(
        Long id,
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        TypeAP typeAP,
        String currentUserEmail,
        String creationDate,
        StatusSolicitation status,
        Long studentId,
        String studentName,
        String feedback,
        String justification,
        Long idManager
) {}