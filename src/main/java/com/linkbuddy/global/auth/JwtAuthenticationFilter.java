package com.linkbuddy.global.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * packageName    : com.linkbuddy.global.auth
 * fileName       : JwtAuthenticationFilter
 * author         : yl951
 * date           : 2024-05-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-19        yl951       최초 생성
 */

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtTokenProvider jwtTokenProvider;

  public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    String token = resolveToken((HttpServletRequest) request);

    log.info(">>>token>>>" + token);
    if (token != null && jwtTokenProvider.validateToken(token)) {
      Authentication authentication = jwtTokenProvider.getAuthentication(token);
      log.info(">>>authentication>>>" + authentication);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    chain.doFilter(request, response);
  }
//  @Override
//  public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//    String token = resolveToken((HttpServletRequest) request);
//
//    log.info(">>>token>>>" + token);
//    log.info(String.valueOf(token == null));
//
//    if (token != null && jwtTokenProvider.validateToken(token)) {
//      Authentication authentication = jwtTokenProvider.getAuthentication(token);
//      log.info(">>>authentication>>>" + authentication);
//      SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
//    chain.doFilter(request, response);
//
//  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
      bearerToken = bearerToken.substring(7); //Bearer 제거
    }
    log.info("bearerToken" + bearerToken);
    return bearerToken;
  }
}
