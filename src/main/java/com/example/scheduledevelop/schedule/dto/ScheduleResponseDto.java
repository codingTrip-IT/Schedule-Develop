package com.example.scheduledevelop.schedule.dto;

import com.example.scheduledevelop.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * ScheduleResponseDto : 일정 응답 DTO
 * - 필드 : 일정 id, 일정 제목, 일정 내용, 회원명, 회원 이메일, 생성일, 수정일
 */
@Getter
public class ScheduleResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final String memberName;
    private final String memberEmail;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ScheduleResponseDto(Long id, String title, String contents, String memberName, String memberEmail, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ScheduleResponseDto toDto(Schedule schedule){
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getMember().getName(),
                schedule.getMember().getEmail(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
