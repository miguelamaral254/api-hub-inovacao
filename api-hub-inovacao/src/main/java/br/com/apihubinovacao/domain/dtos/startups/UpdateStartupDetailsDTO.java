package br.com.apihubinovacao.domain.dtos.startups;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;

import java.util.List;

public record UpdateStartupDetailsDTO(
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        String cnpj,
        List<CoauthorDTO> coauthors
) {
}
