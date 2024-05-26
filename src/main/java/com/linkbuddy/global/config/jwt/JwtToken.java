package com.linkbuddy.global.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.linkbuddy.global.config.jwt
 * fileName       : JwtToken
 * author         : admin
 * date           : 2024-05-12
 * description    : JwtToken DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-12        admin       최초 생성
 */
@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String grantType;   //JWT 인증 타입
    private String accessToken;
    private String refreshToken;
}
