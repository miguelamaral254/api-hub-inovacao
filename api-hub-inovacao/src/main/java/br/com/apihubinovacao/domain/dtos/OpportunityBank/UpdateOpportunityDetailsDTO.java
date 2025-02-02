package br.com.apihubinovacao.domain.dtos.OpportunityBank;

public record UpdateOpportunityDetailsDTO(
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink

) {
}
