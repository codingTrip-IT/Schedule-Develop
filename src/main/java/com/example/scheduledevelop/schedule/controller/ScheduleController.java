package com.example.scheduledevelop.schedule.controller;

import com.example.scheduledevelop.global.SessionConst;
import com.example.scheduledevelop.schedule.service.ScheduleService;
import com.example.scheduledevelop.member.entity.Member;
import com.example.scheduledevelop.schedule.dto.ScheduleSaveRequestDto;
import com.example.scheduledevelop.schedule.dto.UpdateTitleAndContentsRequestDto;
import com.example.scheduledevelop.global.page.CustomResponsePage;
import com.example.scheduledevelop.schedule.dto.FindAllScheduleResponseDto;
import com.example.scheduledevelop.schedule.dto.ScheduleResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> createSchedule(
        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
        @Valid @RequestBody ScheduleSaveRequestDto requestDto) {

        ScheduleResponseDto scheduleResponseDto
                = scheduleService.save(requestDto.getTitle(), requestDto.getContents(), loginMember);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/schedules")
    public ResponseEntity<CustomResponsePage<FindAllScheduleResponseDto>> getSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(scheduleService.findAll(page, size));
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable("scheduleId") Long id) {
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateTitleAndContents(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @PathVariable("scheduleId") Long id,
            @Valid @RequestBody UpdateTitleAndContentsRequestDto requestDto
    ) {
        ScheduleResponseDto scheduleResponseDto
                = scheduleService.updateTitleContents(id, requestDto.getTitle(), requestDto.getContents(), loginMember);

        return ResponseEntity.ok(scheduleResponseDto);
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @PathVariable("scheduleId") Long id
    ){
        scheduleService.delete(id,loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
