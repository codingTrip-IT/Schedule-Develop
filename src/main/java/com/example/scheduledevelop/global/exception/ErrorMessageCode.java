package com.example.scheduledevelop.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessageCode {
    OK("200", "ok", null),
    BAD_REQUEST("400", "잘못된 요청입니다.", "ko"),
    UNAUTHORIZED("401", "인증이 필요합니다.", "ko"),
    FORBIDDEN("403", "권한이 없습니다.", "ko"),
    NOT_FOUND("404", "요청한 리소스를 찾을 수 없습니다.", "ko"),
    INTERNAL_SERVER_ERROR("5000", "서버 내부 오류가 발생했습니다.", "ko");

    private final String code;
    private final String message;
    private final String languageCode;

}