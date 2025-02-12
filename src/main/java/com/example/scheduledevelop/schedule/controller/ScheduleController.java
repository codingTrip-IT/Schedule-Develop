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

/**
 * ScheduleController : 일정 CRUD 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정 생성
     * @param requestDto 사용자가 입력한 일정 정보
     * @param loginMember 세션 로그인 멤버
     * @return 성공 시 HTTP 201 CREATED
     */
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @Valid @RequestBody ScheduleSaveRequestDto requestDto,
             @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
        ) {

        ScheduleResponseDto scheduleResponseDto
                = scheduleService.save(requestDto.getTitle(), requestDto.getContents(), loginMember);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    /**
     * 일정 목록 조회
     * @param page 페이지 번호(기본값 0)
     * @param size 페이지 크기(기본값 10)
     * @return 성공 시 HTTP 200 OK
     */
    @GetMapping("/schedules")
    public ResponseEntity<CustomResponsePage<FindAllScheduleResponseDto>> getSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(scheduleService.findAll(page, size));
    }

    /**
     * 일정 선택 조회
     * @param id 일정 id
     * @return 성공 시 HTTP 200 OK
     */
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable("scheduleId") Long id) {
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    /**
     * 일정 선택 수정(내용만 수정)
     * @param id 일정 id
     * @param requestDto 사용자가 입력한 일정 정보
     * @param loginMember 세션 로그인 멤버
     * @return 성공 시 HTTP 200 OK
     */
    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateTitleAndContents(
            @PathVariable("scheduleId") Long id,
            @Valid @RequestBody UpdateTitleAndContentsRequestDto requestDto,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
            ) {
        ScheduleResponseDto scheduleResponseDto
                = scheduleService.updateTitleContents(id, requestDto.getTitle(), requestDto.getContents(), loginMember);

        return ResponseEntity.ok(scheduleResponseDto);
    }

    /**
     * 일정 선택 삭제
     * @param id 일정 id
     * @param loginMember 세션 로그인 멤버
     * @return 성공 시 HTTP 200 OK
     */
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable("scheduleId") Long id,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
            ){
        scheduleService.delete(id,loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
