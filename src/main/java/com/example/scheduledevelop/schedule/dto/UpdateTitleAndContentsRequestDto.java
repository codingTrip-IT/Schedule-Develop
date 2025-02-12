package com.example.scheduledevelop.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * UpdateTitleAndContentsRequestDto : 일정 수정 요청 DTO(제목, 내용)
 * - 필드 : 일정 제목(필수값, 20자 이내 인증 처리), 일정 내용(필수값, 200자 이내 인증 처리)
 */
@Getter
public class UpdateTitleAndContentsRequestDto {

    @NotBlank(message = "title는 필수값입니다.")
    @Size(max = 20, message = "title은 20자 이내로 입력하세요.")
    private final String title;

    @NotBlank(message = "contents는 필수값입니다.")
    @Size(max = 200, message = "contents은 200자 이내로 입력하세요.")
    private final String contents;

    public UpdateTitleAndContentsRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
