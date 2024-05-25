package com.linkbuddy.global.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.linkbuddy.global.entity
 * fileName       : JWTToken
 * author         : yl951
 * date           : 2024-05-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-16        yl951       최초 생성
 */
@Builder
@Data
@AllArgsConstructor
public class JwtToken {

  private String grantType;
  private String accessToken;
  private String refreshToken;
}
