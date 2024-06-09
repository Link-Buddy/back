//package com.linkbuddy.global.config.oauth;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
///**
// * packageName    : com.linkbuddy.global.config.oauth
// * fileName       : OAuth2SuccessHandler
// * author         : yl951
// * date           : 2024-06-06
// * description    :
// * ===========================================================
// * DATE              AUTHOR             NOTE
// * -----------------------------------------------------------
// * 2024-06-06        yl951       최초 생성
// */
//@RequiredArgsConstructor
//@Component
//public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
//  private final TokenProvider tokenProvider;
//  private static final String URI = "/auth/success";
//
//
//  @Override
//  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//    String accessToken = tokenProvider.
//  }
//}
