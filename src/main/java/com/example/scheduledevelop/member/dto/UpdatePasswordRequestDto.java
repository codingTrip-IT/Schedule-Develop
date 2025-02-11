package com.example.scheduledevelop.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

    @NotBlank(message = "oldPassword는 필수값입니다.")
    private final String oldPassword;
    @NotBlank(message = "newPassword는 필수값입니다.")
    private final String newPassword;

    public UpdatePasswordRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
