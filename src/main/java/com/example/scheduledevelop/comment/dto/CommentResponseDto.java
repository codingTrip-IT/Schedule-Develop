package com.example.scheduledevelop.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * CommentResponseDto : 댓글 응답 DTO
 * - 필드 : 댓글 id, 일정 제목, 일정 내용, 회원명, 회원 이메일, 댓글 내용, 생성일, 수정일
 */
@Getter
public class CommentResponseDto {

    private final Long id;
    private final String scheduleTitle;
    private final String scheduleContents;
    private final String memberName;
    private final String memberEmail;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CommentResponseDto(Long id, String scheduleTitle, String scheduleContents, String memberName, String memberEmail, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContents = scheduleContents;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
