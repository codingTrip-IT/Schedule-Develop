package com.example.scheduledevelop.presentation.dto;

import com.example.scheduledevelop.domain.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class FindAllScheduleResponseDto {

    private final String title;
    private final String contents;
    private final Long countComment;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String memberName;

    public FindAllScheduleResponseDto(String title, String contents, Long countComment, LocalDateTime createdAt, LocalDateTime modifiedAt, String memberName) {
        this.title = title;
        this.contents = contents;
        this.countComment = countComment;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.memberName = memberName;
    }
}
