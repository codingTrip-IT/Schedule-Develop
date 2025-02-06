package com.example.scheduledevelop.presentation.dto;

import lombok.Getter;

@Getter
public class UpdateTitleContentsRequestDto {

    private final String title;

    private final String contents;

    public UpdateTitleContentsRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
