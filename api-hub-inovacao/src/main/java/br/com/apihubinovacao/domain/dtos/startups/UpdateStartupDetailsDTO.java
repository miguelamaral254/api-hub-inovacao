package br.com.apihubinovacao.domain.dtos.startups;

public record UpdateStartupDetailsDTO(
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        String cnpj
) {
}
