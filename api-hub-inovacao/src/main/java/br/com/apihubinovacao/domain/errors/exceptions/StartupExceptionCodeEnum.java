package br.com.apihubinovacao.domain.errors.exceptions;

import lombok.Getter;

@Getter
public enum StartupExceptionCodeEnum {

    DUPLICATE_CNPJ("Duplicate CNPJ", "STAREXCEP-001", 409),
    STARTUP_NOT_FOUND("Startup not found", "STAREXCEP-002", 404),
    STARTUP_CREATION_FAILED("Failed to create Startup", "STAREXCEP-004", 500),
    STARTUP_UPDATE_FAILED("Failed to update Startup", "STAREXCEP-005", 500),
    INVALID_STARTUP_DATA("The Startup data is missing or incomplete", "STAREXCEP-006", 400),
    INVALID_STARTUP_TITLE("The Startup title is required", "STAREXCEP-007", 400),
    INVALID_STARTUP_DESCRIPTION("The Startup description is required", "STAREXCEP-008", 400),
    INVALID_STARTUP_STATUS("Invalid startup status", "STAREXCEP-009", 400);
    private final String message;
    private final String code;
    private final int httpStatus;

    StartupExceptionCodeEnum(String message, String code, int httpStatus) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;

    }
}
