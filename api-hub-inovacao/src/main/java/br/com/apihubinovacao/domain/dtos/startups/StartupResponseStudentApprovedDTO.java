package br.com.apihubinovacao.domain.dtos.startups;

import br.com.apihubinovacao.domain.dtos.coauthor.CoauthorDTO;

import java.util.List;

public record StartupResponseStudentApprovedDTO(
        Long id,
        String title,
        String description,
        String urlPhoto,
        String pdfLink,
        String siteLink,
        String cnpj,
        String currentUserEmail,
        String creationDate,
        Long studentId,
        String studentName,
        List<CoauthorDTO> coauthors
) {
}
