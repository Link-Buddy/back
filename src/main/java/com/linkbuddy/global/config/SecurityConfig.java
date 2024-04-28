package com.linkbuddy.global.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * packageName    : com.linkbuddy.global.config
 * fileName       : SecurityConfig
 * author         : yjkim
 * date           : 2024-04-27
 * description    : Spring Security 설정
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-27        admin       최초 생성
 */
@Configuration
@EnableWebSecurity  //모든 요청 URL이 Spring Security 제어를 받도록 설정
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new SecurityPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .anyRequest().authenticated() // 모든 요청에 대해서 인증 설정
                ).formLogin(login -> login
                        .defaultSuccessUrl("/test", true)
                        .permitAll()
                ).logout(Customizer.withDefaults());
        return http.build();
    }
}
