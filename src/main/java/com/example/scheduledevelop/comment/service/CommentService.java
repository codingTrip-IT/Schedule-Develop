package com.example.scheduledevelop.comment.service;

import com.example.scheduledevelop.comment.entity.Comment;
import com.example.scheduledevelop.global.exception.ApiError;
import com.example.scheduledevelop.global.exception.ApplicationException;
import com.example.scheduledevelop.global.exception.CustomErrorMessageCode;
import com.example.scheduledevelop.global.exception.ErrorMessageCode;
import com.example.scheduledevelop.member.entity.Member;
import com.example.scheduledevelop.member.repository.MemberRepository;
import com.example.scheduledevelop.schedule.entity.Schedule;
import com.example.scheduledevelop.comment.repository.CommentRepository;
import com.example.scheduledevelop.comment.dto.CommentResponseDto;
import com.example.scheduledevelop.comment.dto.CommentSaveResponseDto;
import com.example.scheduledevelop.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * CommentService : 댓글 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    /**
     * 댓글 생성
     * @param contents 뎃글 내용
     * @param scheduleId 일정 id
     * @param loginMember 세션 로그인 멤버
     * @return CommentSaveResponseDto 댓글 생성 응답 DTO 반환
     */
    @Transactional
    public CommentSaveResponseDto save(String contents, Long scheduleId, Member loginMember) {
        Schedule schedule = getScheduleByIdOrElseThrow(scheduleId);
        
        Comment comment = new Comment(contents, schedule, loginMember);
        Comment savedComment = commentRepository.save(comment);
        return new CommentSaveResponseDto(
                savedComment.getId(),
                savedComment.getContents(),
                savedComment.getSchedule().getTitle(),
                savedComment.getSchedule().getContents(),
                savedComment.getMember().getName(),
                savedComment.getMember().getEmail(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    /**
     * 댓글 목록 조회
     * @param scheduleId 일정 id
     * commentList: 일정 id에 해당하는 댓글 조회 리스트
     * @return CommentResponseDto 댓글 응답 DTO 리스트 반환
     */
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll(Long scheduleId) {
        List<Comment> commentList = commentRepository.findAllBySchedule_Id(scheduleId);

        List<CommentResponseDto> dtos = new ArrayList<>();
        for (Comment comment : commentList) {
            dtos.add(new CommentResponseDto(
                        comment.getId(),
                        comment.getSchedule().getTitle(),
                        comment.getSchedule().getContents(),
                        comment.getMember().getName(),
                        comment.getMember().getEmail(),
                        comment.getContents(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                    )
            );
        }
        return dtos;
    }
    
    /**
     * 댓글 선택 조회
     * @param commentId 댓글 id
     * getCommentByIdOrElseThrow : 댓글 id로 댓글 조회
     * @return CommentResponseDto 댓글 응답 DTO 리스트 반환
     */
    @Transactional(readOnly = true)
    public CommentResponseDto findById(Long commentId) {
        Comment comment = getCommentByIdOrElseThrow(commentId);
        
        return new CommentResponseDto(
                commentId,
                comment.getSchedule().getTitle(),
                comment.getSchedule().getContents(),
                comment.getMember().getName(),
                comment.getMember().getEmail(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    /**
     * 댓글 선택 수정(내용만 수정)
     * @param commentId 댓글 id
     * @param contents 댓글 내용
     * @param loginMember 세션 로그인 멤버
     * getCommentByIdOrElseThrow : 댓글 id로 댓글 조회
     * validateMemberId : 본인(작성자) 검증 로직
     * @return CommentResponseDto 댓글 응답 DTO 반환
     */
    @Transactional
    public CommentResponseDto updateContents(Long commentId, String contents, Member loginMember) {
        Comment comment = getCommentByIdOrElseThrow(commentId);

        validateMemberId(loginMember, comment);

        log.info("댓글 수정 전={}", comment.getContents());
        comment.updateContents(contents);
        log.info("댓글 수정 후={}", comment.getContents());

        return new CommentResponseDto(
                commentId,
                comment.getSchedule().getTitle(),
                comment.getSchedule().getContents(),
                comment.getMember().getName(),
                comment.getMember().getEmail(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    /**
     * 댓글 선택 수정(내용만 수정)
     * @param commentId 댓글 id
     * @param loginMember 세션 로그인 멤버
     * getCommentByIdOrElseThrow : 댓글 id로 댓글 조회
     * validateMemberId : 본인(작성자) 검증 로직
     */
    @Transactional
    public void delete(Long commentId, Member loginMember) {
        Comment comment = getCommentByIdOrElseThrow(commentId);
        validateMemberId(loginMember, comment);
        commentRepository.deleteById(commentId);
    }

    /**
     * 일정 id로 일정 조회
     * @param scheduleId 일정 id
     * @exception ApplicationException 해당 id를 찾을 수 없는 경우, 404 예외처리(커스텀 예외처리 ID_NOT_FOUND)
     * @return 일정 id에 해당하는 일정 반환
     */
    private Schedule getScheduleByIdOrElseThrow(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );
    }

    /**
     * 댓글 id로 댓글 조회
     * @param commentId 댓글 id
     * @exception ApplicationException 해당 id를 찾을 수 없는 경우, 404 예외처리(커스텀 예외처리 ID_NOT_FOUND)
     * @return 일정 id에 해당하는 댓글 반환
     */
    private Comment getCommentByIdOrElseThrow(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );
    }

    /**
     * 본인(작성자)만 본인(작성자) 검증 로직
     * @param loginMember 세션 로그인 멤버
     * @param comment 댓글
     * loginMember(로그인 정보)의 id와 DB에서 조회한 id 비교
     * @exception ApplicationException 각각의 id가 불일치할 경우 403 예외처리(커스텀 예외처리 NOT_OWNER)
     */
    private static void validateMemberId(Member loginMember, Comment comment) {
        Long findMemberId = comment.getMember().getId();

        if (loginMember.getId() != findMemberId){
            throw new ApplicationException(ErrorMessageCode.FORBIDDEN,
                    List.of(new ApiError(CustomErrorMessageCode.NOT_OWNER.getStatus(),
                            CustomErrorMessageCode.NOT_OWNER.getMessage())));
        }
    }
}
