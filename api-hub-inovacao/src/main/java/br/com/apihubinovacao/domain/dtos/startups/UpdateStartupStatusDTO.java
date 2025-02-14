package br.com.apihubinovacao.domain.dtos.startups;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;

public record UpdateStartupStatusDTO(
        StatusSolicitation status,
        String feedback,
        String justification,
        Long idManager
) {
}
