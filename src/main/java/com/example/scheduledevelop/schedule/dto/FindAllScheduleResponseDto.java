package com.example.scheduledevelop.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * FindAllScheduleResponseDto : 일정 목록 조회 응답 DTO
 * - 필드 : 일정 id, 일정 제목, 일정 내용, 댓글수, 생성일, 수정일, 회원명
 */
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
