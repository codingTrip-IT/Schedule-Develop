package com.example.scheduledevelop.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * SignUpRequestDto : 회원 생성 요청 DTO
 * - 필드 : 회원명(필수값, 10자 이내 인증 처리), 이메일(이메일 형식, 필수값 인증 처리), 비밀번호(필수값, 20자 이내 인증 처리)
 */
@Getter
public class SignUpRequestDto {

    @NotBlank(message = "name은 필수값입니다.")
    @Size(max = 10,message = "name은 10자 이내로 입력하세요.")
    private String name;

    @Email(message = "email 형식이 올바르지 않습니다.")
    @NotBlank(message = "email은 필수값입니다.")
    private String email;

    @NotBlank(message = "password는 필수값입니다.")
    @Size(max = 20,message = "password 20자 이내로 입력하세요.")
    private String password;

}
