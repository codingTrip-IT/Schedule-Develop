package com.example.scheduledevelop.Auth.controller;

import com.example.scheduledevelop.SessionConst;
import com.example.scheduledevelop.Auth.service.AuthService;
import com.example.scheduledevelop.global.config.WebConfig;
import com.example.scheduledevelop.member.entity.Member;
import com.example.scheduledevelop.Auth.dto.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final WebConfig.PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletRequest request){

       Member member= authService.findByEmail(requestDto.getEmail());
       if (member == null){
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
       String encyptPassword = member.getPassword();

       if (passwordEncoder.matches(requestDto.getPassword(),encyptPassword)){
            Member loginMember = authService.login(requestDto.getEmail(), encyptPassword);

            //로그인 성공 처리
            //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
            HttpSession session = request.getSession();

            //세션에 로그인 회원 정보 보관
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

            return new ResponseEntity<>(HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
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
