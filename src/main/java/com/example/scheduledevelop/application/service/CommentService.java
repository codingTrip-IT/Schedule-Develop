package com.example.scheduledevelop.application.service;

import com.example.scheduledevelop.domain.entity.Comment;
import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.domain.entity.Schedule;
import com.example.scheduledevelop.domain.repository.CommentRepository;
import com.example.scheduledevelop.presentation.dto.CommentSaveRequestDto;
import com.example.scheduledevelop.presentation.dto.CommentSaveResponseDto;
import com.example.scheduledevelop.presentation.dto.ScheduleResponseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
