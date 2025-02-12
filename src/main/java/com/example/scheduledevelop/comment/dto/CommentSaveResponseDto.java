package com.example.scheduledevelop.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * CommentSaveResponseDto : 댓글 생성 응답 DTO
 * - 필드 : 댓글 id, 댓글 내용, 일정 제목, 일정 내용, 회원명, 회원 이메일, 생성일, 수정일
 */
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
