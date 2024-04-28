package com.linkbuddy.global.config;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * packageName    : com.linkbuddy.global.config
 * fileName       : SecurityPasswordEncoder
 * author         : admin
 * date           : 2024-04-27
 * description    : Security password encoder 설정
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-27        admin       최초 생성
 */
public class SecurityPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
