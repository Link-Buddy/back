package com.linkbuddy.global.config.jwt;

import com.linkbuddy.domain.user.UserService;
import com.linkbuddy.global.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.linkbuddy.global.config.jwt
 * fileName       : SecurityUtil
 * author         : yjkim
 * date           : 2024-08-31
 * description    : Security Auth 인증정보 가공
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-31-031        admin       최초 생성
 */
@Component
@Slf4j
public class SecurityUtil {
  private UserService userService;

  public SecurityUtil(UserService userService) {
    this.userService = userService;
  }

  public Long getCurrentUserId() throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
      throw new RuntimeException("인증 정보가 없습니다.");
    }
    String email = authentication.getName();
    log.info("email = {}", email);
    User user = userService.find(email);
    log.info("user = {}", user);

    if (user == null) {
      throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
    }
    return user.getId();
  }
}
