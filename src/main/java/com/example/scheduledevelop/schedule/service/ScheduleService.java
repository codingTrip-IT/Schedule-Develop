package com.example.scheduledevelop.schedule.service;

import com.example.scheduledevelop.global.exception.ApiError;
import com.example.scheduledevelop.global.exception.ApplicationException;
import com.example.scheduledevelop.global.exception.CustomErrorMessageCode;
import com.example.scheduledevelop.global.exception.ErrorMessageCode;
import com.example.scheduledevelop.member.entity.Member;
import com.example.scheduledevelop.schedule.entity.Schedule;
import com.example.scheduledevelop.comment.repository.CommentRepository;
import com.example.scheduledevelop.schedule.repository.ScheduleRepository;
import com.example.scheduledevelop.global.page.CustomResponsePage;
import com.example.scheduledevelop.schedule.dto.FindAllScheduleResponseDto;
import com.example.scheduledevelop.schedule.dto.ScheduleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ScheduleResponseDto save(String title, String contents, Member loginMember) {

        Schedule schedule = new Schedule(title, contents, loginMember);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getMember().getName(),
                savedSchedule.getMember().getEmail(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public CustomResponsePage<FindAllScheduleResponseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Schedule> schedulePage = scheduleRepository.findAllByOrderByModifiedAtDesc(pageable);
        Page<FindAllScheduleResponseDto> dtoPage = schedulePage.map(
                schedule -> {
                    Member member = schedule.getMember();
                    long countComment = commentRepository.countBySchedule_Id(schedule.getId());

                    return new FindAllScheduleResponseDto(
                            schedule.getId(),
                            schedule.getTitle(),
                            schedule.getContents(),
                            countComment, //댓글 개수
                            schedule.getCreatedAt(),
                            schedule.getModifiedAt(),
                            member.getName()
                    );
                });
        return new CustomResponsePage<>(dtoPage);
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                             CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getMember().getName(),
                schedule.getMember().getEmail(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

//    @Transactional(readOnly = true)
//    public Schedule findSchedule(Long scheduleId) {
//        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
//                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
//                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getCode(),
//                                             CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
//        );
//        return schedule;
//    }

    @Transactional
    public ScheduleResponseDto updateTitleContents(Long id, String title, String contents, Member loginMember) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                             CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );
        Long findMemberId = schedule.getMember().getId();
        log.info("fineMemberId={}",findMemberId);
        log.info("loginMember.getId()={}",loginMember.getId());

        // 작성자만 수정 가능
        if (loginMember.getId() != findMemberId){
//            throw new IllegalArgumentException("해당 일정의 작성자가 아닙니다.");
//            new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw new ApplicationException(ErrorMessageCode.FORBIDDEN,
                    List.of(new ApiError(CustomErrorMessageCode.NOT_OWNER.getStatus(),
                                         CustomErrorMessageCode.NOT_OWNER.getMessage())));
        }

        schedule.updateTitleContents(title, contents);

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getMember().getName(),
                schedule.getMember().getEmail(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long id, Member loginMember) {
//        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
//        if (!scheduleRepository.existsById(id)){
//            throw new IllegalArgumentException("해당 id가 존재하지 않습니다.");
//        }
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorMessageCode.NOT_FOUND,
                        List.of(new ApiError(CustomErrorMessageCode.ID_NOT_FOUND.getStatus(),
                                             CustomErrorMessageCode.ID_NOT_FOUND.getMessage())))
        );

        Long findMemberId = schedule.getMember().getId();
        log.info("fineMemberId={}",findMemberId);
        log.info("loginMember.getId()={}",loginMember.getId());

        // 작성자만 수정 가능
        if (loginMember.getId() != findMemberId){
            throw new ApplicationException(ErrorMessageCode.FORBIDDEN,
                    List.of(new ApiError(CustomErrorMessageCode.NOT_OWNER.getStatus(),
                                         CustomErrorMessageCode.NOT_OWNER.getMessage())));
        }

        scheduleRepository.deleteById(id);
    }
}
