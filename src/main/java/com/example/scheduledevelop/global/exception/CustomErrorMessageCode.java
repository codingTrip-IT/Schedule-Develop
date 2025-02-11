package com.example.scheduledevelop.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomErrorMessageCode {

    ID_NOT_FOUND("ID_NOT_FOUND", "해당 id를 찾을 수 없습니다."),
    NOT_OWNER("NOT_OWNER", "본인(작성자)이 아닌 경우 권한이 없습니다."),
    INVALID_PASSWORD("INVALID_PASSWORD", "비밀번호가 일치하지 않습니다.");

    private final String code;
    private final String message;
}
