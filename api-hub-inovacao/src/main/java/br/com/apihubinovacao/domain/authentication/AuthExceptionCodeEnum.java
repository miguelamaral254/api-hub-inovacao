package br.com.apihubinovacao.domain.authentication;

import br.com.apihubinovacao.core.ExceptionCode;
import lombok.Getter;

@Getter
public enum AuthExceptionCodeEnum implements ExceptionCode {

    SERVER_ERROR("Internal server error", "ERR-500", 500),
    INVALID_TOKEN("Invalid token", "ERR-401", 401),
    INVALID_REQUEST("Invalid request parameters", "ERR-400", 400);

    private final String message;
    private final String code;
    private final int httpStatus;

    AuthExceptionCodeEnum(String message, String code, int httpStatus) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }
}