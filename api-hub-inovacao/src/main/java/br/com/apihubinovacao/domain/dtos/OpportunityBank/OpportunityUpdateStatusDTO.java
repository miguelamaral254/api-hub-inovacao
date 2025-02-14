package br.com.apihubinovacao.domain.dtos.OpportunityBank;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;

import java.time.LocalDate;

public record OpportunityUpdateStatusDTO(
        StatusSolicitation status,
        LocalDate validationDate,
        String feedback,
        String justification,
        Long idManager
) {

}
