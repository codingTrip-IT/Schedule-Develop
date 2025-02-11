package com.example.scheduledevelop.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentSaveResponseDto {

    private final Long id;
    private final String contents;
    private final String scheduleTitle;
    private final String scheduleContents;
    private final String memberName;
    private final String memberEmail;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentSaveResponseDto(Long id, String contents, String scheduleTitle, String scheduleContents, String memberName, String memberEmail, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.contents = contents;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContents = scheduleContents;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
