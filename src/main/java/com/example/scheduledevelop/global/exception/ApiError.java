package com.example.scheduledevelop.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ApiError : 발생한 오류 정보를 담아 반환하는 클래스
 * - 필드 : 코드, 메시지
 */
@Getter
@AllArgsConstructor
public class ApiError {
    private String code;
    private String message;
}