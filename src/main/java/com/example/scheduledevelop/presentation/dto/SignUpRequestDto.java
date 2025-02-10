package com.example.scheduledevelop.presentation.dto;

import com.example.scheduledevelop.config.WebConfig;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    @NotBlank(message = "name은 필수값입니다.")
    @Size(max = 10,message = "name은 10자 이내로 입력하세요.")
    private final String name;

    @Email(message = "email 형식이 올바르지 않습니다.")
    @NotBlank(message = "email은 필수값입니다.")
    private final String email;

    @NotBlank(message = "password는 필수값입니다.")
    private final String password;

    public SignUpRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
