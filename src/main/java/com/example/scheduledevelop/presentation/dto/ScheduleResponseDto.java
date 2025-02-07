package com.example.scheduledevelop.presentation.dto;

import com.example.scheduledevelop.domain.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final String memberName;
    private final String memberEmail;

    public ScheduleResponseDto(Long id, String title, String contents, String memberName, String memberEmail) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
    }

    public static ScheduleResponseDto toDto(Schedule schedule){
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getMember().getName(),
                schedule.getMember().getEmail());
    }
}
