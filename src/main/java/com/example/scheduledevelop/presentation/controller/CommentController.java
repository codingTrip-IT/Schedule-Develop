package com.example.scheduledevelop.presentation.controller;

import com.example.scheduledevelop.SessionConst;
import com.example.scheduledevelop.application.service.CommentService;
import com.example.scheduledevelop.application.service.MemberService;
import com.example.scheduledevelop.application.service.ScheduleService;
import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.domain.entity.Schedule;
import com.example.scheduledevelop.presentation.dto.CommentResponseDto;
import com.example.scheduledevelop.presentation.dto.CommentSaveRequestDto;
import com.example.scheduledevelop.presentation.dto.CommentSaveResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final ScheduleService scheduleService;
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentSaveResponseDto> save(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @PathVariable("scheduleId") Long scheduleId,
            @Valid @RequestBody CommentSaveRequestDto requestDto
    ){
        Schedule schedule = scheduleService.findSchedule(scheduleId);

        // 생성 로직
        CommentSaveResponseDto commentSaveResponseDto
                = commentService.save(requestDto.getContents(),schedule,loginMember);

        return new ResponseEntity<>(commentSaveResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findAll(
            @PathVariable("scheduleId") Long scheduleId
    ) {
        Schedule schedule = scheduleService.findSchedule(scheduleId);
        return ResponseEntity.ok(commentService.findAll(schedule));
    }
}
