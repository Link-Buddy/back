package com.linkbuddy.global.config.oauth;

import com.linkbuddy.global.config.jwt.JwtToken;
import com.linkbuddy.global.config.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 * packageName    : com.linkbuddy.global.config.oauth
 * fileName       : OAuth2SuccessHandler
 * author         : yl951
 * date           : 2024-06-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-06        yl951       최초 생성
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
  private final JwtTokenProvider tokenProvider;
  private static final String URI = "/user/loginSuccess";


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    //token 발급
    JwtToken token = tokenProvider.createToken(authentication);

    String redirectUrl = UriComponentsBuilder.fromUriString(URI)
            .queryParam("accessToken", token.getAccessToken())
            .queryParam("refreshToken", token.getRefreshToken())
            .build().toUriString();

    response.sendRedirect(redirectUrl);

  }
}
