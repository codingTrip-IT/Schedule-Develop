package com.example.scheduledevelop.application.service;

import com.example.scheduledevelop.domain.entity.Schedule;
import com.example.scheduledevelop.domain.repository.ScheduleRepository;
import com.example.scheduledevelop.presentation.dto.ScheduleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

//    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto save(String username, String title, String contents) {

        Schedule schedule = new Schedule(username, title, contents);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getUsername(),
                savedSchedule.getTitle(),
                savedSchedule.getContents());
    }

    public List<ScheduleResponseDto> findAll() {

        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::toDto)
                .toList();
    }
}
