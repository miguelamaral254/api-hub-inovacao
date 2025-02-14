package br.com.apihubinovacao.domain.dtos.startups;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeAP;

import java.util.List;

public record StartupCreateProfessorDTO(
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        String userEmail,
        StatusSolicitation status,
        Long professorId,
        List<CoauthorDTO> coauthors,
        String cnpj) {
}
