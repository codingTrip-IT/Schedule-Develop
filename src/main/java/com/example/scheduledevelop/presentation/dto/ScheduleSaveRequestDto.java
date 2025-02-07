package com.example.scheduledevelop.presentation.dto;

import lombok.Getter;

@Getter
public class ScheduleSaveRequestDto {

    private final String title;
    private final String contents;

    public ScheduleSaveRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
