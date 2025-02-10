package com.example.scheduledevelop.application.service;

import com.example.scheduledevelop.domain.entity.Comment;
import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.domain.entity.Schedule;
import com.example.scheduledevelop.domain.repository.CommentRepository;
import com.example.scheduledevelop.domain.repository.ScheduleRepository;
import com.example.scheduledevelop.presentation.dto.CommentResponseDto;
import com.example.scheduledevelop.presentation.dto.CommentSaveResponseDto;
import com.example.scheduledevelop.presentation.dto.ScheduleResponseDto;
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

    @Transactional
    public CommentSaveResponseDto save(String contents, Schedule schedule, Member loginMember) {

        Comment comment = new Comment(contents, schedule , loginMember);

        Comment savedComment = commentRepository.save(comment);
        return new CommentSaveResponseDto(
                savedComment.getId(),
                savedComment.getContents(),
                savedComment.getSchedule().getTitle(),
                savedComment.getSchedule().getContents(),
                savedComment.getMember().getName(),
                savedComment.getMember().getEmail()
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll(Schedule schedule) {

        List<Comment> commentList = commentRepository.findAllBySchedule_Id(schedule.getId());
        List<CommentResponseDto> dtos = new ArrayList<>();
        for (Comment comment : commentList) {
            dtos.add(new CommentResponseDto(comment.getId(),
                    comment.getSchedule().getTitle(),
                    comment.getSchedule().getContents(),
                    comment.getMember().getName(),
                    comment.getMember().getEmail(),
                    comment.getContents())
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
                comment.getContents()
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
                comment.getContents()
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
