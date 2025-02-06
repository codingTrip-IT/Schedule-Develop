package com.example.scheduledevelop.presentation.controller;

import com.example.scheduledevelop.application.service.MemberService;
import com.example.scheduledevelop.presentation.dto.CreateMemberRequestDto;
import com.example.scheduledevelop.presentation.dto.CreateMemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<CreateMemberResponseDto> save(@RequestBody CreateMemberRequestDto requestDto){

        CreateMemberResponseDto createMemberResponseDto
                 = memberService.save(requestDto.getName(),requestDto.getEmail());

        return new ResponseEntity<>(createMemberResponseDto, HttpStatus.CREATED);
    }

}
