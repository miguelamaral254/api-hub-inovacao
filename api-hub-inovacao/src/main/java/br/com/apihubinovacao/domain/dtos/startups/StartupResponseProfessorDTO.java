package br.com.apihubinovacao.domain.dtos.startups;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;
import br.com.apihubinovacao.domain.enums.StatusSolicitation;
import br.com.apihubinovacao.domain.enums.TypeAP;

import java.util.List;

public record StartupResponseProfessorDTO(
        Long id,
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        String currentUserEmail,
        String creationDate,
        StatusSolicitation status,
        Long professorId,
        String professorName,
        String feedback,
        String justification,
        Long idManager,
        String cnpj,
        List<CoauthorDTO> coauthors) {
}
