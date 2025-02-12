package com.example.scheduledevelop.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * ApplicationException: 애플리케이션에서 발생하는 예외를 처리하는 커스텀 예외 클래스 (RuntimeException 상속받음)
 * - 예외 발생 시 HTTP 상태 코드, 오류 메시지 코드, 상세 오류 목록 반환
 */
@Getter
public class ApplicationException extends RuntimeException {
    private HttpStatus status;
    private ErrorMessageCode errorMessageCode;
    private List<ApiError> errors;

    public ApplicationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    // 추가적인 오류 목록을 포함하는 생성자
    public ApplicationException(ErrorMessageCode errorMessageCode, List<ApiError> errors) {
        super(errorMessageCode.getMessage());
        this.errorMessageCode = errorMessageCode;
        this.errors = errors != null ? errors : Collections.emptyList();
    }
}