package com.example.scheduledevelop.presentation.dto;

import lombok.Getter;

@Getter
public class ScheduleSaveRequestDto {

    private final String title;

    private final String contents;

    private final String memberEmail;

    public ScheduleSaveRequestDto(String title, String contents, String memberEmail) {
        this.title = title;
        this.contents = contents;
        this.memberEmail = memberEmail;
    }

}
