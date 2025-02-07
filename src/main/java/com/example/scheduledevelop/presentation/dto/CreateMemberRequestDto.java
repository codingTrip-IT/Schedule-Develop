package com.example.scheduledevelop.presentation.dto;

import lombok.Getter;

@Getter
public class CreateMemberRequestDto {

    private final String name;
    private final String email;
    private final String password;

    public CreateMemberRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
