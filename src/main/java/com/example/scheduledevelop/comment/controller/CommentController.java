package com.example.scheduledevelop.comment.controller;

import com.example.scheduledevelop.SessionConst;
import com.example.scheduledevelop.comment.service.CommentService;
import com.example.scheduledevelop.schedule.service.ScheduleService;
import com.example.scheduledevelop.member.entity.Member;
import com.example.scheduledevelop.schedule.entity.Schedule;
import com.example.scheduledevelop.comment.dto.CommentResponseDto;
import com.example.scheduledevelop.comment.dto.CommentSaveRequestDto;
import com.example.scheduledevelop.comment.dto.CommentSaveResponseDto;
import com.example.scheduledevelop.comment.dto.UpdateContentsRequestDto;
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
        //서비스로 옮기기
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

    @GetMapping("/schedules/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> findById(
            @PathVariable("commentId") Long commentId
    ) {
        return ResponseEntity.ok(commentService.findById(commentId));
    }

    @PatchMapping("/schedules/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateContents(
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody UpdateContentsRequestDto requestDto
            ) {
        return ResponseEntity.ok(commentService.updateContents(commentId, requestDto.getContents()));
    }

    @DeleteMapping("/schedules/comments/{commentId}")
    public void delete(
            @PathVariable("commentId") Long commentId
    ) {
        commentService.delete(commentId);
    }
}
