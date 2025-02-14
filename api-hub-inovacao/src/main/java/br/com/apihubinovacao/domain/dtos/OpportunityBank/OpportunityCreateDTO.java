package br.com.apihubinovacao.domain.dtos.OpportunityBank;

import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeBO;

import java.time.LocalDate;

public record OpportunityCreateDTO(
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        TypeBO typeBO,
        String authorEmail,
        StatusSolicitation status,
        boolean flagActive,
        Long partnerCompanyId,
        String feedback,
        String justification,   
        Long idManager
) {}