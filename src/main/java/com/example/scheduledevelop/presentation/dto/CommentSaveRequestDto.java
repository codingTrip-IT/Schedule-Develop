package com.example.scheduledevelop.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentSaveRequestDto {

    @NotBlank(message = "contents는 필수값입니다.")
    @Size(max = 200, message = "contents은 200자 이내로 입력하세요.")
    private String contents;
}
