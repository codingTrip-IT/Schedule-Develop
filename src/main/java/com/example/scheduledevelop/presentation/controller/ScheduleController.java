package com.example.scheduledevelop.presentation.controller;

import com.example.scheduledevelop.SessionConst;
import com.example.scheduledevelop.application.service.ScheduleService;
import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.presentation.dto.ScheduleSaveRequestDto;
import com.example.scheduledevelop.presentation.dto.ScheduleResponseDto;
import com.example.scheduledevelop.presentation.dto.UpdateTitleAndContentsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(
        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
        @RequestBody ScheduleSaveRequestDto requestDto) {

        // 생성 로직
        ScheduleResponseDto scheduleResponseDto
                = scheduleService.save(requestDto.getTitle(), requestDto.getContents(),loginMember);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {

        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();

        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable("scheduleId") Long id) {

        ScheduleResponseDto scheduleResponseDto = scheduleService.findById(id);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateTitleAndContents(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @PathVariable("scheduleId") Long id,
            @RequestBody UpdateTitleAndContentsRequestDto requestDto
    ) {

        Member findMember = scheduleService.findMember(id);
        Long findMemberId = findMember.getId();

        // 작성자만 수정 가능
        if (!loginMember.getId().equals(findMemberId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 수정 로직
        scheduleService.updateTitleContents(id, requestDto.getTitle(), requestDto.getContents());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> delete(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @PathVariable("scheduleId") Long id
    ){

        Member findMember = scheduleService.findMember(id);
        Long findMemberId = findMember.getId();

        // 작성자만 삭제 가능
        if (!loginMember.getId().equals(findMemberId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 삭제 로직
        scheduleService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
