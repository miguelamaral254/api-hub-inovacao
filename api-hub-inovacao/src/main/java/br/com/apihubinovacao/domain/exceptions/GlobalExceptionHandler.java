package br.com.apihubinovacao.domain.exceptions;

import br.com.apihubinovacao.domain.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getHttpStatus()))
                .body(new ErrorResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        ErrorCodeEnum error = ErrorCodeEnum.SERVER_ERROR;
        return ResponseEntity.status(HttpStatus.valueOf(error.getHttpStatus()))
                .body(new ErrorResponse(error.getCode(), error.getMessage()));
    }

    static class ErrorResponse {
        private final String code;
        private final String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
