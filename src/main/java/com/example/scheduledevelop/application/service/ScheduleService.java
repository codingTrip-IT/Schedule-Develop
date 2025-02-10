package com.example.scheduledevelop.application.service;

import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.domain.entity.Schedule;
import com.example.scheduledevelop.domain.repository.CommentRepository;
import com.example.scheduledevelop.domain.repository.ScheduleRepository;
import com.example.scheduledevelop.presentation.dto.CustomPageResponseDto;
import com.example.scheduledevelop.presentation.dto.CustomResponsePage;
import com.example.scheduledevelop.presentation.dto.FindAllScheduleResponseDto;
import com.example.scheduledevelop.presentation.dto.ScheduleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ScheduleResponseDto save(String title, String contents, Member member) {

        Schedule schedule = new Schedule(title, contents, member);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getMember().getName(),
                savedSchedule.getMember().getEmail()
        );
    }


//    할일 제목, 할일 내용, 댓글 개수, 일정 작성일, 일정 수정일, 일정 작성 유저명 필드를 조회합니다.
    @Transactional(readOnly = true)
    public CustomResponsePage<FindAllScheduleResponseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Schedule> schedulePage = scheduleRepository.findAllByOrderByModifiedAtDesc(pageable);
        Page<FindAllScheduleResponseDto> dtoPage = schedulePage.map(
                schedule -> {
                    Member member = schedule.getMember();
                    long countComment = commentRepository.countBySchedule_Id(schedule.getId());

                    return new FindAllScheduleResponseDto(
                            schedule.getTitle(),
                            schedule.getContents(),
                            countComment,
                            schedule.getCreatedAt(),
                            schedule.getModifiedAt(),
                            member.getName()
                    );
                });
        return new CustomResponsePage<>(dtoPage);
    }


//    @Transactional(readOnly = true)
//    public List<ScheduleResponseDto> findAll() {
//
//        return scheduleRepository.findAll()
//                .stream()
//                .map(ScheduleResponseDto::toDto)
//                .toList();
//    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findById(Long id) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleResponseDto(
                findSchedule.getId(),
                findSchedule.getTitle(),
                findSchedule.getContents(),
                findSchedule.getMember().getName(),
                findSchedule.getMember().getEmail()
        );
    }

    @Transactional(readOnly = true)
    public Member findMember(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        return findSchedule.getMember();
    }

    @Transactional(readOnly = true)
    public Schedule findSchedule(Long scheduleId) {
        return scheduleRepository.findByIdOrElseThrow(scheduleId);
    }

    @Transactional
    public void updateTitleContents(Long id, String title, String contents) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        findSchedule.updateTitleContents(title, contents);
    }

    @Transactional
    public void delete(Long id) {

//        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        if (!scheduleRepository.existsById(id)){
            throw new IllegalArgumentException("해당 id가 존재하지 않습니다.");
        }

        scheduleRepository.deleteById(id);
    }
}
