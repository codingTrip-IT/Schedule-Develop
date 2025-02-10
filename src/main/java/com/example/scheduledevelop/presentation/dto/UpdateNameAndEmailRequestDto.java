package com.example.scheduledevelop.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateNameAndEmailRequestDto {

    @NotBlank(message = "name은 필수값입니다.")
    @Size(max = 10,message = "name은 10자 이내로 입력하세요.")
    private String name;

    @Email(message = "email 형식이 올바르지 않습니다.")
    @NotBlank(message = "email은 필수값입니다.")
    private String email;
}
