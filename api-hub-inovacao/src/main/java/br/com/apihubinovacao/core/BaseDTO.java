package br.com.apihubinovacao.core;

import java.time.LocalDateTime;

public interface BaseDTO {

    Long id();

    String name();

    LocalDateTime createdDate();

    LocalDateTime lastModifiedDate();

}