package com.example.scheduledevelop.presentation.controller;

import com.example.scheduledevelop.SessionConst;
import com.example.scheduledevelop.application.service.CommentService;
import com.example.scheduledevelop.application.service.MemberService;
import com.example.scheduledevelop.application.service.ScheduleService;
import com.example.scheduledevelop.config.WebConfig;
import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.domain.entity.Schedule;
import com.example.scheduledevelop.presentation.dto.CommentSaveRequestDto;
import com.example.scheduledevelop.presentation.dto.CommentSaveResponseDto;
import com.example.scheduledevelop.presentation.dto.ScheduleResponseDto;
import com.example.scheduledevelop.presentation.dto.ScheduleSaveRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final ScheduleService scheduleService;
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentSaveResponseDto> save(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @PathVariable("scheduleId") Long id,
            @Valid @RequestBody CommentSaveRequestDto requestDto
    ){
        Schedule schedule = scheduleService.findSchedule(id);

        // 생성 로직
        CommentSaveResponseDto commentSaveResponseDto
                = commentService.save(requestDto.getContents(),schedule,loginMember);

        return new ResponseEntity<>(commentSaveResponseDto, HttpStatus.CREATED);
    }



}
