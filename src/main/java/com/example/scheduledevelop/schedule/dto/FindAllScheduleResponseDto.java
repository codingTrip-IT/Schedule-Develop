package com.example.scheduledevelop.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FindAllScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final Long countComment;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String memberName;

    public FindAllScheduleResponseDto(Long id, String title, String contents, Long countComment, LocalDateTime createdAt, LocalDateTime modifiedAt, String memberName) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.countComment = countComment;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.memberName = memberName;
    }
}
