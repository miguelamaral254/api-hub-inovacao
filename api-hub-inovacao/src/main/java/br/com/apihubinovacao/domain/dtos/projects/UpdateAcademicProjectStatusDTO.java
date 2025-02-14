package br.com.apihubinovacao.domain.dtos.projects;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;

public record UpdateAcademicProjectStatusDTO(
        StatusSolicitation status,
        String feedback,
        String justification,
        Long idManager
) {}