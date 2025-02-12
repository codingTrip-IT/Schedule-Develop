package com.example.scheduledevelop.global.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.scheduledevelop.global.filter.LoginFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig : 웹 애플리케이션의 전역 설정을 담당
 * - 로그인 필터 등록
 * - 비밀번호 암호화 및 검증을 위한 PasswordEncoder 정의
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 로그인 필터를 등록하는 Bean
     * @return FilterRegistrationBean (필터 등록 정보 포함)
     */
    @Bean
    public FilterRegistrationBean loginFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    /**
     * PasswordEncoder 클래스: 비밀번호 암호화 및 검증을 담당
     * - encode : BCrypt 알고리즘을 사용하여 비밀번호를 해싱(암호화)
     * - matches : 사용자가 입력한 비밀번호와 저장된 암호화된 비밀번호를 비교
     */
    @Component
    public class PasswordEncoder {

        public String encode(String rawPassword) {
            return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
        }

        public boolean matches(String rawPassword, String encodedPassword) {
            BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
            return result.verified;
        }
    }
}
