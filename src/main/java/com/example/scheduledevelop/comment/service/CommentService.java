package com.example.scheduledevelop.comment.service;

import com.example.scheduledevelop.comment.entity.Comment;
import com.example.scheduledevelop.global.exception.ApiError;
import com.example.scheduledevelop.global.exception.ApplicationException;
import com.example.scheduledevelop.global.exception.CustomErrorMessageCode;
import com.example.scheduledevelop.global.exception.ErrorMessageCode;
import com.example.scheduledevelop.member.entity.Member;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentSaveResponseDto save(String contents, Long scheduleId, Member loginMember) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getCode(),
                                CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );

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

    @Transactional(readOnly = true)
    public CommentResponseDto findById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 id 값이 없습니다.")
        );

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

    @Transactional
    public CommentResponseDto updateContents(Long commentId, String contents) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 id 값이 없습니다.")
        );

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

    @Transactional
    public void delete(Long commentId) {
        if (!commentRepository.existsById(commentId)){
            throw new IllegalArgumentException("해당 id가 존재하지 않습니다.");
        }
        commentRepository.deleteById(commentId);
    }
}
