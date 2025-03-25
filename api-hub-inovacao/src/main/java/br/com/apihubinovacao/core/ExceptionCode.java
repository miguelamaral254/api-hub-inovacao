package br.com.apihubinovacao.core;

public interface ExceptionCode {
    String getCode();
    String getMessage();
    int getHttpStatus();
}