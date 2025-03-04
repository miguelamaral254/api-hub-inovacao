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
    FILE_UPLOAD_FAILED("Failed to upload", "ERR-030", 400),
    IMAGE_SIZE_EXCEEDED("The image size exceeds the maximum limit of 5MB.", "ERR-046", 400),
    MANAGER_NOT_FOUND("Manager not found", "ERR-100", 404),

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
    INVALID_OPPORTUNITY_DATA("The opportunity data is missing or incomplete", "ERR-060", 400),
    INVALID_OPPORTUNITY_TITLE("The opportunity title is required", "ERR-061", 400),
    INVALID_OPPORTUNITY_DESCRIPTION("The opportunity description is required", "ERR-062", 400),
    INVALID_PARTNER_COMPANY("A valid partner company is required for this opportunity", "ERR-063", 400),
    // CNPJ e CPF

    INVALID_CNPJ("Invalid CNPJ format or missing", "ERR-009", 400),
    INVALID_CPF("Invalid CPF format or missing", "ERR-011", 400),
    // Editais


    INVALID_PUBLISH_DATA("The publish data is missing.", "ERR-039", 400),
    INVALID_PUBLISH_TITLE("The publish title is required.", "ERR-040", 400),
    INVALID_PUBLISH_DESCRIPTION("The publish description is required.", "ERR-041", 400),
    INVALID_PUBLISH_ACCESS_LINK("The publish access link is required.", "ERR-042", 400),
    INVALID_PUBLISH_INITIAL_DATE("The publish start date is required.", "ERR-043", 400),
    INVALID_PUBLISH_FINAL_DATE("The publish end date is required.", "ERR-044", 400),
    INVALID_PUBLISH_DATE_RANGE("The publish end date cannot be earlier than the start date.", "ERR-045", 400),

    // Role inválida
    INVALID_ROLE("Invalid user role", "ERR-012", 400),

    // Projeto
    PROJECT_NOT_FOUND("Project not found", "ERR-019", 404),
    PROJECT_ALREADY_EXISTS("Project already exists", "ERR-050", 409),
    AUTHOR_NOT_FOUND("Author not found", "ERR-023", 404),

    // Edital
    PUBLISH_NOT_FOUND("Publish not found", "ERR-024", 404),
    //STARTUP
    STARTUP_NOT_FOUND("Startup not found", "ERR-019", 404),

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