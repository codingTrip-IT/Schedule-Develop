package com.example.scheduledevelop.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleSaveRequestDto {

    @NotBlank(message = "title는 필수값입니다.")
    @Size(max = 20, message = "title은 20자 이내로 입력하세요.")
    private final String title;

    @NotBlank(message = "contents는 필수값입니다.")
    @Size(max = 200, message = "contents은 200자 이내로 입력하세요.")
    private final String contents;

    public ScheduleSaveRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
