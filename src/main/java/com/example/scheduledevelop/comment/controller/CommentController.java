package com.example.scheduledevelop.comment.controller;

import com.example.scheduledevelop.global.SessionConst;
import com.example.scheduledevelop.comment.service.CommentService;
import com.example.scheduledevelop.member.entity.Member;
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

    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentSaveResponseDto> createComment(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @PathVariable("scheduleId") Long scheduleId,
            @Valid @RequestBody CommentSaveRequestDto requestDto
    ){
        CommentSaveResponseDto commentSaveResponseDto
                = commentService.save(requestDto.getContents(),scheduleId,loginMember);

        return new ResponseEntity<>(commentSaveResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(
            @PathVariable("scheduleId") Long scheduleId
    ) {
        return ResponseEntity.ok(commentService.findAll(scheduleId));
    }

    @GetMapping("/schedules/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(
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
    public void deleteComment(
            @PathVariable("commentId") Long commentId
    ) {
        commentService.delete(commentId);
    }
}
