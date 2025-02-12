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

/**
 * CommentController : 댓글 CRUD 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 생성
     * @param requestDto 사용자가 입력한 contents 정보
     * @param scheduleId 일정 id
     * @param loginMember 세션 로그인 멤버
     * @return 성공 시 HTTP 201 CREATED
     */
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentSaveResponseDto> createComment(
            @Valid @RequestBody CommentSaveRequestDto requestDto,
            @PathVariable("scheduleId") Long scheduleId,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
            ){
        CommentSaveResponseDto commentSaveResponseDto
                = commentService.save(requestDto.getContents(),scheduleId,loginMember);

        return new ResponseEntity<>(commentSaveResponseDto, HttpStatus.CREATED);
    }

    /**
     * 댓글 목록 조회
     * @param scheduleId 일정 id
     * @return 성공 시 HTTP 200 OK
     */
    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(
            @PathVariable("scheduleId") Long scheduleId
    ) {
        return ResponseEntity.ok(commentService.findAll(scheduleId));
    }

    /**
     * 댓글 선택 조회
     * @param commentId 댓글 id
     * @return 성공 시 HTTP 200 OK
     */
    @GetMapping("/schedules/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(
            @PathVariable("commentId") Long commentId
    ) {
        return ResponseEntity.ok(commentService.findById(commentId));
    }

    /**
     * 댓글 선택 수정(내용만 수정)
     * @param commentId 댓글 id
     * @param requestDto 사용자가 입력한 contents 정보
     * @param loginMember 세션 로그인 멤버
     * @return 성공 시 HTTP 200 OK
     */
    @PatchMapping("/schedules/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateContents(
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody UpdateContentsRequestDto requestDto,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
    ) {
        return ResponseEntity.ok(commentService.updateContents(commentId, requestDto.getContents(), loginMember));
    }

    /**
     * 댓글 선택 삭제
     * @param commentId 댓글 id
     * @param loginMember 세션 로그인 멤버
     * @return 성공 시 HTTP 200 OK
     */
    @DeleteMapping("/schedules/comments/{commentId}")
    public void deleteComment(
            @PathVariable("commentId") Long commentId,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
    ) {
        commentService.delete(commentId,loginMember);
    }
}
