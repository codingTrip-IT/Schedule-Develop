package com.example.scheduledevelop.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.scheduledevelop.presentation.filter.LoginFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean loginFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

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
