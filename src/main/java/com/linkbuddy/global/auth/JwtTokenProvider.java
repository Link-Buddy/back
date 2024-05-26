package com.linkbuddy.global.auth;

import com.linkbuddy.global.auth.JwtToken;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * packageName    : com.linkbuddy.global.config
 * fileName       : JwtTokenProvider
 * author         : yl951
 * date           : 2024-05-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-16        yl951       최초 생성
 */
@Slf4j
@Component
public class JwtTokenProvider {
  private final Key key;

  // application에서 secret 값 가져와서 key에 저장
  public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
    log.info("this.key=========================11111" + key);
  }


  // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
  public JwtToken generateJwtToken(String email) {

//    log.info("authoritie1111" + authentication.getAuthorities());
//    log.info("authoritie2222" + authentication.getPrincipal());
//    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//    // 권한 가져오기
//    String authorities = authentication.getAuthorities().stream()
//            .map(GrantedAuthority::getAuthority)
//            .collect(Collectors.joining(","));

    // log.info("REREauthorities" + authorities);
    long now = (new Date()).getTime();
    log.info("this.key=========================" + key);
    // Access Token 생성
    long accessTokenValidity = 60 * 60 * 1000L;
    Date accessTokenExpiresIn = new Date(now + accessTokenValidity); //한시간
    String accessToken = Jwts.builder()
            .setSubject(email)
            //        .claim("auth", authorities)
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

    // Refresh Token 생성
    long refreshTokenValidity = 14 * 24 * 60 * 60 * 1000L;
    String refreshToken = Jwts.builder()
            .setExpiration(new Date(now + refreshTokenValidity)) //일주일
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

    return JwtToken.builder()
            .grantType("Bearer")
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }


  // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
  public Authentication getAuthentication(String accessToken) {
    // Jwt 토큰 복호화
    Claims claims = parseClaims(accessToken);
    String email = claims.getSubject();
    log.info("==============" + claims + email);
//    log.info(claims.get("auth").toString());
//
//    Collection<? extends GrantedAuthority> authorities =
//            Arrays.stream(claims.get("auth").toString().split(","))
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());

    // UserDetails 객체를 만들어서 Authentication return
    // UserDetails: interface, User: UserDetails를 구현한 class
    UserDetails principal = new User(claims.getSubject(), "", new ArrayList<>());
    log.info("==============1111" + principal.getAuthorities());
    return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
  }

  // 토큰 정보를 검증하는 메서드
  public boolean validateToken(String token) {
    try {
      log.info("222>>>" + key);
      Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token);


      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT Token", e);
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT Token", e);
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT Token", e);
    } catch (IllegalArgumentException e) {
      log.info("JWT claims string is empty.", e);
    } catch (Exception e) {

      log.info(">>>ERRE>>>", e);
    }
    return false;
  }


  // key를 통해 JWT파서 생성 -> 유저로부터 받은 accessToken 복호화해서 클레임 추출
  // 만약 만료된 토큰이면 유효시간 예외
  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(accessToken)
              .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

}
