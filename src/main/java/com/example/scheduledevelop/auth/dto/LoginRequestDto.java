package com.example.scheduledevelop.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * LoginRequestDto : 로그인 요청 DTO
 * - 필드 : 이메일(이메일 형식, 필수값 인증 처리), 비밀번호(필수값 인증처리)
 */
@Getter
public class LoginRequestDto {

    @Email(message = "email 형식이 올바르지 않습니다.")
    @NotBlank(message = "email은 필수값입니다.")
    private final String email;
    @NotBlank(message = "password는 필수값입니다.")
    private final String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
