package com.linkbuddy.domain.user;

import com.linkbuddy.domain.user.repository.UserRepository;
import com.linkbuddy.global.config.jwt.JwtToken;
import com.linkbuddy.global.config.jwt.JwtTokenProvider;
import com.linkbuddy.global.entity.User;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AuthenticationManagerBuilder authenticationManagerBuilder;
  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  PasswordEncoder passwordEncoder;

  public User create(User user) throws Exception {
    try {
      String encodePassword = passwordEncoder.encode(user.getPassword());
      System.out.println("encodePassword" + encodePassword);
      User newUser = User.builder()
              .email(user.getEmail())
              .name(user.getName())
              .password(encodePassword)
              .statusCd(10)   // default
              .build();
      userRepository.save(newUser);
      return user;

    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  public User find(String email) throws Exception {
    try {
      log.info("email = {}", email);
      return userRepository.customFindByEmail(email);
    } catch (Exception e) {
      throw new Exception();
    }
  }

  @Transactional
  public JwtToken signIn(String email, String password) {
    // 1. Authentication 객체 생성
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
    // 2. authenticate 메서드를 통해 요청된 회원정보에 대한 검증 -> loadUserByUsername 메서드 실행됨
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    // 3. 검증에 성공하면 인증된 Authentication 객체 기반으로 JWT 토큰 생성
    JwtToken jwtToken = jwtTokenProvider.createToken(authentication);

    return jwtToken;
  }
}
