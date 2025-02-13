package com.example.scheduledevelop.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * UpdatePasswordRequestDto : 회원 비밀번호 수정 요청 DTO
 * - 필드 : 수정 전 비밀번호(필수값, 20자 이내 인증 처리), 수정 후 비밀번호(필수값, 20자 이내 인증 처리)
 */
@Getter
public class UpdatePasswordRequestDto {

    @NotBlank(message = "oldPassword는 필수값입니다.")
    @Size(max = 20,message = "oldPassword 20자 이내로 입력하세요.")
    private String oldPassword;
    @NotBlank(message = "newPassword는 필수값입니다.")
    @Size(max = 20,message = "newPassword 20자 이내로 입력하세요.")
    private String newPassword;

}
