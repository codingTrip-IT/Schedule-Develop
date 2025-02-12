package com.example.scheduledevelop.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * CommentSaveRequestDto : 댓글 생성 요청 DTO
 * - 필드 : 댓글 내용(필수값, 200자 이내 인증 처리)
 */
@Getter
public class CommentSaveRequestDto {

    @NotBlank(message = "contents는 필수값입니다.")
    @Size(max = 200, message = "contents은 200자 이내로 입력하세요.")
    private String contents;
}
