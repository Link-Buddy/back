package com.linkbuddy.global.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * packageName    : com.linkbuddy.global.auth
 * fileName       : SecurityUtil
 * author         : yl951
 * date           : 2024-05-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-26        yl951       최초 생성
 */
public class SecurityUtil {
  public static String getCurrentUsername() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || authentication.getName() == null) {
      throw new RuntimeException("No authentication information.");
    }

    return authentication.getName();
  }
}
