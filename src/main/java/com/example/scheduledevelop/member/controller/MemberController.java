package com.example.scheduledevelop.member.controller;

import com.example.scheduledevelop.global.SessionConst;
import com.example.scheduledevelop.member.service.MemberService;
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

/**
 * MemberController : 회원 CRUD 컨트롤러
 */
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 생성(회원가입)
     * @param requestDto 사용자가 입력한 회원 정보
     * @return 성공 시 HTTP 201 CREATED
     */
    @PostMapping("/members/signup")
    public ResponseEntity<SingUpResponseDto> createMember(@Valid @RequestBody SignUpRequestDto requestDto){

        SingUpResponseDto singUpResponseDto
                 = memberService.save(requestDto.getName(),requestDto.getEmail(),requestDto.getPassword());

        return new ResponseEntity<>(singUpResponseDto, HttpStatus.CREATED);
    }

    /**
     * 회원 목록 조회
     * @return 성공 시 HTTP 200 OK
     */
    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> getMembers(){
        return ResponseEntity.ok(memberService.findAll());
    }

    /**
     * 회원 선택 조회
     * @param id 회원 id
     * @return 성공 시 HTTP 200 OK
     */
    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable("memberId") Long id){
        return ResponseEntity.ok(memberService.findById(id));
    }

    /**
     * 회원 선택 수정(회원명, 이메일 수정)
     * @param id 회원 id
     * @param requestDto 사용자가 입력한 회원명, 이메일 정보
     * @param loginMember 세션 로그인 멤버
     * @return 성공 시 HTTP 200 OK
     */
    @PatchMapping("/members/{memberId}")
    public ResponseEntity<MemberResponseDto> updateNameAndEmail(
            @PathVariable("memberId") Long id,
            @Valid @RequestBody UpdateNameAndEmailRequestDto requestDto,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
        ){

        MemberResponseDto memberResponseDto
                = memberService.updateNameAndEmail(id,requestDto.getName(),requestDto.getEmail(),loginMember);

        return ResponseEntity.ok(memberResponseDto);
    }

    /**
     * 회원 선택 수정(비밀번호 수정)
     * @param id 회원 id
     * @param requestDto 사용자가 입력한 비밀번호 정보
     * @param loginMember 세션 로그인 멤버
     * @return 성공 시 HTTP 200 OK
     */
    @PatchMapping("/members/{memberId}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable("memberId") Long id,
            @Valid @RequestBody UpdatePasswordRequestDto requestDto,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember
    ){

        memberService.updatePassword(id,requestDto.getOldPassword(), requestDto.getNewPassword(),loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 회원 선택 삭제
     * @param id 회원 id
     * @param loginMember 세션 로그인 멤버
     * @return 성공 시 HTTP 200 OK
     */
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Void> deleteMember(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
            @PathVariable("memberId") Long id){

        memberService.delete(id,loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
