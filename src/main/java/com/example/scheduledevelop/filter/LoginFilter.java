package com.example.scheduledevelop.filter;

import com.example.scheduledevelop.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/members/signup","/login"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        log.info("로그인 필터 로직 실행");

        // whiteListURL에 포함된 경우 true 반환 -> !true = false
        if (!isWhiteList(requestURI)){
            log.info("인증 체크 로직 실행 {}", requestURI);
            HttpSession session = httpRequest.getSession(false);

            if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
                throw new RuntimeException("로그인 해주세요");
            }
        }

        // 로그인 성공 로직
        log.info("로그인에 성공했습니다.");

        chain.doFilter(request,response);

    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST,requestURI);
    }
}
