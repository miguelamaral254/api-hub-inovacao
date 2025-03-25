package br.com.apihubinovacao.domain.opportunity;

import br.com.apihubinovacao.core.ExceptionCode;
import lombok.Getter;

@Getter
public enum OpportunityExceptionCodeEnum implements ExceptionCode {

    OPPORTUNITY_NOT_FOUND("Opportunity not found", "OPPEXCEP-001", 404),
    OPPORTUNITY_ALREADY_EXISTS("Opportunity already exists", "OPPEXCEP-002", 409),
    MANAGER_NOT_FOUND("Manager not found", "OPPEXCEP-003", 404),
    OPPORTUNITY_CREATION_FAILED("Failed to create opportunity", "OPPEXCEP-004", 500),
    OPPORTUNITY_UPDATE_FAILED("Failed to update opportunity", "OPPEXCEP-005", 500),
    INVALID_OPPORTUNITY_DATA("The opportunity data is missing or incomplete", "OPPEXCEP-006", 400),
    INVALID_OPPORTUNITY_TITLE("The opportunity title is required", "OPPEXCEP-007", 400),
    INVALID_OPPORTUNITY_DESCRIPTION("The opportunity description is required", "OPPEXCEP-008", 400),
    IMAGE_CREATION_FAILED("Failed to save image", "OPPEXCEP-009", 400),
    OPPORTUNITY_URL_ALREADY_EXISTS("Opportunity URL already exists", "OPPEXCEP-010", 409),
    INVALID_OPPORTUNITY_STATUS("Invalid opportunity status", "OPPEXCEP-011", 400);

    private final String message;
    private final String code;
    private final int httpStatus;

    OpportunityExceptionCodeEnum(String message, String code, int httpStatus) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }
}