package com.example.scheduledevelop.auth.controller;

import com.example.scheduledevelop.global.SessionConst;
import com.example.scheduledevelop.auth.service.AuthService;
import com.example.scheduledevelop.global.config.WebConfig;
import com.example.scheduledevelop.member.entity.Member;
import com.example.scheduledevelop.auth.dto.LoginRequestDto;
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

/**
 * AuthController : 로그인 및 로그아웃을 처리하는 컨트롤러
 * - 로그인 성공 시 세션 생성, 사용자 정보 저장
 * - 로그아웃 시 세션 삭제
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final WebConfig.PasswordEncoder passwordEncoder;

    /**
     * 로그인 기능
     * @param requestDto 사용자가 입력한 이메일과 비밀번호 정보
     * @param request HTTP 요청 객체 (세션 관리용)
     * @return 인증 성공 시 HTTP 200 OK, 실패 시 HTTP 401 UNAUTHORIZED 반환
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletRequest request){

       Member member= authService.findByEmail(requestDto.getEmail());
        // 회원 정보가 없으면 로그인 실패 (401 반환)
       if (member == null){
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
       String encyptPassword = member.getPassword();

       if (passwordEncoder.matches(requestDto.getPassword(),encyptPassword)){
            Member loginMember = authService.login(requestDto.getEmail(), encyptPassword);

            //로그인 성공 처리 : 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
            HttpSession session = request.getSession();

            //세션에 로그인 회원 정보 보관
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

            return new ResponseEntity<>(HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
    }

    /**
     * 로그아웃 기능
     * @param request HTTP 요청 객체 (세션 관리용)
     * @return HTTP 200 OK (로그아웃 성공)
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        //세션 삭제
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
