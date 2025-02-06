package com.example.scheduledevelop.presentation.controller;

import com.example.scheduledevelop.application.service.MemberService;
import com.example.scheduledevelop.presentation.dto.CreateMemberRequestDto;
import com.example.scheduledevelop.presentation.dto.CreateMemberResponseDto;
import com.example.scheduledevelop.presentation.dto.MemberResponseDto;
import com.example.scheduledevelop.presentation.dto.UpdateNameEmailRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> findAll(){

        List<MemberResponseDto> memberResponseDtoList = memberService.findAll();

        return new ResponseEntity<>(memberResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable("memberId") Long id){
        MemberResponseDto memberResponseDto = memberService.findById(id);
        return new ResponseEntity<>(memberResponseDto,HttpStatus.OK);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<Void> updateNameEmail(
            @PathVariable("memberId") Long id,
            @RequestBody UpdateNameEmailRequestDto requestDto){

        memberService.updateNameEmail(id,requestDto.getName(),requestDto.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
