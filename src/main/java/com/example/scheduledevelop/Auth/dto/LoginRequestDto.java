package com.example.scheduledevelop.Auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

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
