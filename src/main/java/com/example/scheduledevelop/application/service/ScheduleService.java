package com.example.scheduledevelop.application.service;

import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.domain.entity.Schedule;
import com.example.scheduledevelop.domain.repository.MemberRepository;
import com.example.scheduledevelop.domain.repository.ScheduleRepository;
import com.example.scheduledevelop.presentation.dto.ScheduleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

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

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAll() {

        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::toDto)
                .toList();
    }

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

    @Transactional
    public void updateTitleContents(Long id, String title, String contents) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        findSchedule.updateTitleContents(title, contents);
    }

    @Transactional
    public void delete(Long id) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        scheduleRepository.delete(findSchedule);
    }
}
