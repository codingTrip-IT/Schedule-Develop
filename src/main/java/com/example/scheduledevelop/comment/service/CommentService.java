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
                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
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
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                             CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
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
    public CommentResponseDto updateContents(Long commentId, String contents, Member loginMember) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                             CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );

        Long findMemberId = comment.getMember().getId();

        // 작성자만 수정 가능
        if (loginMember.getId() != findMemberId){
            throw new ApplicationException(ErrorMessageCode.FORBIDDEN,
                    List.of(new ApiError(CustomErrorMessageCode.NOT_OWNER.getStatus(),
                            CustomErrorMessageCode.NOT_OWNER.getMessage())));
        }

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
    public void delete(Long commentId, Member loginMember) {
//        if (!commentRepository.existsById(commentId)){
//            throw new ApplicationException(ErrorMessageCode.NOT_FOUND,
//                    List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getCode(),
//                                         CustomErrorMessageCode.ID_NOT_FOUND.getMessage())));
//        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );

        Long findMemberId = comment.getMember().getId();

        // 작성자만 수정 가능
        if (loginMember.getId() != findMemberId){
            throw new ApplicationException(ErrorMessageCode.FORBIDDEN,
                    List.of(new ApiError(CustomErrorMessageCode.NOT_OWNER.getStatus(),
                            CustomErrorMessageCode.NOT_OWNER.getMessage())));
        }
        commentRepository.deleteById(commentId);
    }
}
