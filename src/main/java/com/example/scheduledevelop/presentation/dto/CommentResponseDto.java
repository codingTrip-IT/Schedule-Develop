package com.example.scheduledevelop.presentation.dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final String scheduleTitle;
    private final String scheduleContents;
    private final String memberName;
    private final String memberEmail;
    private final String contents;

    public CommentResponseDto(Long id, String scheduleTitle, String scheduleContents, String memberName, String memberEmail, String contents) {
        this.id = id;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContents = scheduleContents;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.contents = contents;
    }
}
