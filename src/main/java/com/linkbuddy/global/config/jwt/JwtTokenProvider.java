package com.linkbuddy.global.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * packageName    : com.linkbuddy.global.config.jwt
 * fileName       : JwtTokenProvider
 * author         : admin
 * date           : 2024-05-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-12        admin       최초 생성
 */
@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    private static final String AUTHORITIES_KEY = "auth";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 1;  // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    /**
     * application.properties 에서 secret key 값 가져와서 key에 저장
     * @param secretKey
     */
    public JwtTokenProvider(@Value("${jwt.secret.key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Authentication(인증) 객체에 포함되어 있는 권한 정보들을 담은 토큰 생성
     * @param authentication
     * @return
     */
    public JwtToken createToken(Authentication authentication) {
        log.info("authentication = {}", authentication);
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();

        log.info("authorities = {}", authorities);

        // Access Token 생성
        Date accessTokenExpires = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpires)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        Date refreshTokenExpires = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpires)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Jwt 토큰 복호화해서 정보 꺼냄
     *  - 토큰의 Claims 에서 권한 정보 추출하고, User 객체 생성해서 Authentication 객체로 반환
     *  - UserDetails 객체를 생성해 주체(subject)와 권한 정보 설정
     * @param accessToken
     * @return
     */
    public Authentication getAuthentication(String accessToken) {
        // jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);
        log.info("claims = {}", claims);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰");
        }
        // 클레임에서 권한 정보 가져옴
//        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//        log.info("authorities = {}", authorities);

        // 역할 설정을 하지 않았기 때문에 빈 목록 반환
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

        // UserDetails 객체 생성해서 Authentication 반환
        // UserDetails, User : spring security - userdetails 클래스
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
     /**
     * 토큰 검증
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            // parserBuilder -> 토큰의 서명 키 설정, 예외 처리를 통해서 토큰 유효성 판단
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace("Bearer ", "").trim());
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {  // 토큰이 올바른 형식이 X or 클레임이 비어 있는 경우 발생
            log.info("JWT Claims string is empty", e);
        }
        return false;
    }

    /**
     * access token 검증 & 파싱
     *  - Claims(클레임) : 토큰에서 사용할 정보의 조각
     *  - accessToken 복호화하고 만료 토큰인 경우 Claims 반환
     *  - parserClaimsJws() : JWT 토큰 검증과 파싱 수행
     * @param accessToken
     * @return
     */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken.replace("Bearer ", "").trim())
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
