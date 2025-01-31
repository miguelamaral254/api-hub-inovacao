package br.com.apihubinovacao.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record PublishCreateDTO(
        String title,
        String description,
        String acessLink,
        String photoLink,
        LocalDate initialDate,
        LocalDate finalDate
) {}
