package br.com.apihubinovacao.domain.enterprise;

import br.com.apihubinovacao.core.ExceptionCode;
import lombok.Getter;

@Getter
public enum EnterpriseExceptionCodeEnum implements ExceptionCode {

    ENTERPRISE_NOT_FOUND("Enterprise not found", "ENTERPRISEEXCEP-001", 404),
    ENTERPRISE_CNPJ_ALREADY_EXISTS("CNPJ already exists", "ENTERPRISEEXCEP-002", 409),
    ENTERPRISE_EMAIL_ALREADY_EXISTS("CNPJ already exists", "ENTERPRISEEXCEP-002", 409),
    INVALID_COMPANY_NAME("Invalid company name", "ENTERPRISEEXCEP-003", 400),
    ENTERPRISE_EMAIL_INVALID("Invalid email format for enterprise", "ENTERPRISEEXCEP-004", 400),
    INVALID_ADDRESS("Invalid address provided", "ENTERPRISEEXCEP-005", 400),
    INVALID_REPRESENTANT_PHONE("Invalid representant phone number", "ENTERPRISEEXCEP-006", 400);

    private final String message;
    private final String code;
    private final int httpStatus;

    EnterpriseExceptionCodeEnum(String message, String code, int httpStatus) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }
}