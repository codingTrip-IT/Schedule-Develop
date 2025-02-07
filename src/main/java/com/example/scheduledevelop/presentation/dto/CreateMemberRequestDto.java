package com.example.scheduledevelop.presentation.dto;

import lombok.Getter;

@Getter
public class CreateMemberRequestDto {

    private final String name;
    private final String email;

    public CreateMemberRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
