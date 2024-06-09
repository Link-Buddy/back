package com.linkbuddy.global.config;

import com.linkbuddy.global.config.jwt.JwtAuthenticationFilter;
import com.linkbuddy.global.config.jwt.JwtTokenProvider;
import com.linkbuddy.global.config.oauth.CustomOAuth2UserService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
@RequiredArgsConstructor
public class SecurityConfig {
  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  @Autowired
  private final CustomOAuth2UserService customOAuth2UserService;

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(httpSecuritySessionManagementConfigurer ->
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 미사용 설정

    http.authorizeHttpRequests(request -> request
                    .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                    .requestMatchers("/user/join", "/user/signIn", "/favicon.ico", "/user/loginSuccess", "/user/loginFailure").permitAll() // 인증 제외할 url 설정
                    .anyRequest().authenticated() // 모든 요청에 대해서 인증 설정
            )

            //ouath2 설정
            .oauth2Login(oauth ->
                    oauth
                            .userInfoEndpoint(c ->
                                    c.userService(customOAuth2UserService)
                            )
                            .defaultSuccessUrl("/user/loginSuccess")
                            .failureUrl("/user/loginFailure")
            )

            //JWT
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // UsernamePasswordAuthenticationFilter 전에 JWT 인증 필터 거치도록 설정
    return http.build();
  }
}
