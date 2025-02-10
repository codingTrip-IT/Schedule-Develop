package com.example.scheduledevelop.presentation.dto;

import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.domain.entity.Schedule;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
public class CommentSaveResponseDto {

    private final Long id;
    private final String contents;
    private final String scheduleTitle;
    private final String scheduleContents;
    private final String memberName;
    private final String memberEmail;

    public CommentSaveResponseDto(Long id, String contents, String scheduleTitle, String scheduleContents, String memberName, String memberEmail) {
        this.id = id;
        this.contents = contents;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContents = scheduleContents;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
    }
}
