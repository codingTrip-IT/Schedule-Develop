package com.example.scheduledevelop.global.filter;

import com.example.scheduledevelop.global.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

/**
 * LoginFilter: 사용자가 로그인을 했는지 확인하는 필터 (Filter 인터페이스 구현)
 * 회원가입, 로그인 요청은 인증 처리에서 제외
 */
@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/members/signup","/login"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        log.info("로그인 필터 로직 실행");

        // WHITE_LIST URL에 포함된 경우 true 반환 -> !true = false
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

    /**
     * 요청 URI가 리스트에 포함되는지 확인하는 메서드
     * @param requestURI 요청 URI
     * @return 화이트리스트에 포함되면 true, 그렇지 않으면 false
     */
    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST,requestURI);
    }
}
