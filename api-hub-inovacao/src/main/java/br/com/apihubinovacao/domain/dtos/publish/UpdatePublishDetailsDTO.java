package br.com.apihubinovacao.domain.dtos.publish;

import java.time.LocalDate;

public record UpdatePublishDetailsDTO(
        String title,
        String description,
        String acessLink,
        String photoLink,
        LocalDate initialDate,
        LocalDate finalDate
) {}
