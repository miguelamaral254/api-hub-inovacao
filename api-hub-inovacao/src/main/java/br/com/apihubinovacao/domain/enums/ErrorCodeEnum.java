package br.com.apihubinovacao.domain.enums;

public enum ErrorCodeEnum {

    // Usuário
    USER_NOT_FOUND("User not found", "ERR-001", 404),
    DUPLICATE_USER("Duplicate user found", "ERR-002", 409),
    DUPLICATE_EMAIL("Duplicate email", "ERR-008", 409),
    INVALID_EMAIL("Invalid email format or missing", "ERR-013", 400),
    DUPLICATE_CNPJ("CNPJ already exists", "ERR-015", 409),
    DUPLICATE_CPF("CPF already exists", "ERR-016", 409),
    DUPLICATE_REGISTRATION("Registration number already exists", "ERR-017", 409),
    INVALID_REGISTRATION("Invalid registration number", "ERR-021", 409),
    PARTNER_COMPANY_NOT_FOUND("Partner company not found", "ERR-024", 404),
    EMAIL_DOES_NOT_MATCH("Email does not match", "ERR-025", 409),

    // Login
    LOGIN_FAILED("Incorrect Email or Password", "ERR-010", 401),

    // Token
    INVALID_TOKEN("Invalid token", "ERR-003", 401),
    UNAUTHORIZED_ACCESS("Unauthorized access", "ERR-004", 403),

    // Requisição
    INVALID_REQUEST("Invalid request parameters", "ERR-005", 400),
    EMAIL_ALREADY_EXISTS("Email already exists", "ERR-006", 409),

    // Senha
    PASSWORD_ENCRYPTION_FAILED("Password encryption failed", "ERR-007", 500),
    INVALID_PASSWORD("Invalid password format or missing", "ERR-014", 400),
    // Telefone
    PHONE_CREATION_FAILED("Failed to create phone record", "ERR-008", 500),
    // Erros de oportunidades
    OPPORTUNITY_NOT_FOUND("Opportunity not found", "ERR-030", 404),
    OPPORTUNITY_CREATION_FAILED("Failed to create opportunity", "ERR-031", 500),
    OPPORTUNITY_UPDATE_FAILED("Failed to update opportunity", "ERR-032", 500),
    // CNPJ e CPF
    INVALID_CNPJ("Invalid CNPJ format or missing", "ERR-009", 400),
    INVALID_CPF("Invalid CPF format or missing", "ERR-011", 400),

    // Role inválida
    INVALID_ROLE("Invalid user role", "ERR-012", 400),
    // Projeto
    PROJECT_NOT_FOUND("Project not found", "ERR-019", 404),
    AUTHOR_NOT_FOUND("Author not found", "ERR-023", 404),
    // Edital
    PUBLISH_NOT_FOUND("Publish not found", "ERR-024", 404),
    // Serviço genérico
    SERVER_ERROR("Internal server error", "ERR-500", 500);


    private final String message;
    private final String code;
    private final int httpStatus;

    ErrorCodeEnum(String message, String code, int httpStatus) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}