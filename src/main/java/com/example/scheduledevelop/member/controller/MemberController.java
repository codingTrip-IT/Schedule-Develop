package com.example.scheduledevelop.member.controller;

import com.example.scheduledevelop.global.SessionConst;
import com.example.scheduledevelop.member.service.MemberService;
import com.example.scheduledevelop.global.config.WebConfig;
import com.example.scheduledevelop.member.entity.Member;
import com.example.scheduledevelop.member.dto.SignUpRequestDto;
import com.example.scheduledevelop.member.dto.UpdateNameAndEmailRequestDto;
import com.example.scheduledevelop.member.dto.UpdatePasswordRequestDto;
import com.example.scheduledevelop.member.dto.MemberResponseDto;
import com.example.scheduledevelop.member.dto.SingUpResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final WebConfig.PasswordEncoder passwordEncoder;

    @PostMapping("/members/signup")
    public ResponseEntity<SingUpResponseDto> createMember(@Valid @RequestBody SignUpRequestDto requestDto){

        SingUpResponseDto singUpResponseDto
                 = memberService.save(requestDto.getName(),requestDto.getEmail(),requestDto.getPassword());

        return new ResponseEntity<>(singUpResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> getMembers(){
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable("memberId") Long id){
        MemberResponseDto memberResponseDto = memberService.findById(id);
        return new ResponseEntity<>(memberResponseDto,HttpStatus.OK);
    }

    @PatchMapping("/members/{memberId}")
    public ResponseEntity<Void> updateNameAndEmail(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @PathVariable("memberId") Long id,
            @Valid @RequestBody UpdateNameAndEmailRequestDto requestDto){

        // 본인만 수정 가능
        if (!loginMember.getId().equals(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        memberService.updateNameAndEmail(id,requestDto.getName(),requestDto.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/members/{memberId}/password")
    public ResponseEntity<Void> updatePassword(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @PathVariable("memberId") Long id,
            @Valid @RequestBody UpdatePasswordRequestDto requestDto){

        // 본인만 수정 가능
        if (!loginMember.getId().equals(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        memberService.updatePassword(id,requestDto.getOldPassword(), requestDto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Void> deleteMember(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @PathVariable("memberId") Long id){

        // 본인만 삭제 가능
        if (!loginMember.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
