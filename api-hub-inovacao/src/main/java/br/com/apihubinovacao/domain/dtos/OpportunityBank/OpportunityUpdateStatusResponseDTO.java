package br.com.apihubinovacao.domain.dtos.OpportunityBank;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeBO;

import java.time.LocalDate;


public record OpportunityUpdateStatusResponseDTO(
        Long id,
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        TypeBO typeBO,
        String authorEmail,
        StatusSolicitation status,
        LocalDate creationDate,
        boolean flagActive,
        Long partnerCompanyId,
        LocalDate validationDate,
        String feedback,
        String justification,
        Long idManager

) {
}
