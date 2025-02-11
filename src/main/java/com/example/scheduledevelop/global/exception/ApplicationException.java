package com.example.scheduledevelop.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

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