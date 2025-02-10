package com.example.scheduledevelop.presentation.controller;

import com.example.scheduledevelop.SessionConst;
import com.example.scheduledevelop.application.service.AuthService;
import com.example.scheduledevelop.domain.entity.Member;
import com.example.scheduledevelop.presentation.dto.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletRequest request){

        Member loginMember = authService.login(requestDto.getEmail(),requestDto.getPassword());

        if (loginMember == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!loginMember.getEmail().equals(requestDto.getEmail()) || !loginMember.getPassword().equals(requestDto.getPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();

        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        //세션을 삭제한다.
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
