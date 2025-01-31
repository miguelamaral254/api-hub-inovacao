package br.com.apihubinovacao.domain.dtos.OpportunityBank;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import java.time.LocalDate;

public record OpportunityCreateDTO(
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        String authorEmail,
        StatusSolicitation status,
        boolean flagActive,
        Long partnerCompanyId
) {}