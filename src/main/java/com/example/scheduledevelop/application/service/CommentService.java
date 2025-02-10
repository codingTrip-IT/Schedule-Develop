package com.example.scheduledevelop.application.service;

import com.example.scheduledevelop.domain.entity.Comment;
import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.domain.entity.Schedule;
import com.example.scheduledevelop.domain.repository.CommentRepository;
import com.example.scheduledevelop.domain.repository.ScheduleRepository;
import com.example.scheduledevelop.presentation.dto.CommentResponseDto;
import com.example.scheduledevelop.presentation.dto.CommentSaveRequestDto;
import com.example.scheduledevelop.presentation.dto.CommentSaveResponseDto;
import com.example.scheduledevelop.presentation.dto.ScheduleResponseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

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
}
