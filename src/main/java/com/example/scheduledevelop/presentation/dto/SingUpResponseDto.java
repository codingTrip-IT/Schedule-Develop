package com.example.scheduledevelop.presentation.dto;

import lombok.Getter;

@Getter
public class SingUpResponseDto {

    private final Long id;
    private final String name;
    private final String email;

    public SingUpResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
