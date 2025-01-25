package br.com.apihubinovacao.domain.enums;

public enum ErrorCodeEnum {

    // Usuário
    USER_NOT_FOUND("User not found", "ERR-001", 404),
    DUPLICATE_USER("Duplicate user found", "ERR-002", 409),
    DUPLICATE_EMAIL("Duplicate email", "ERR-008", 409),

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

    // Telefone
    PHONE_CREATION_FAILED("Failed to create phone record", "ERR-008", 500),

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
