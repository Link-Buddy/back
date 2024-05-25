package com.linkbuddy.domain.user;

import com.linkbuddy.domain.user.repository.UserRepository;
import com.linkbuddy.global.auth.JwtToken;
import com.linkbuddy.global.auth.JwtTokenProvider;
import com.linkbuddy.global.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  PasswordEncoder passwordEncoder;

  @Transactional
  public JwtToken signIn(String username, String password) {
    // 1. username + password 를 기반으로 Authentication 객체 생성
    // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

    // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
    // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    // 3. 인증 정보를 기반으로 JWT 토큰 생성
    JwtToken jwtToken = jwtTokenProvider.generateJwtToken(authentication);

    return jwtToken;
  }

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
      return userRepository.findByEmail(email);
    } catch (Exception e) {
      throw new Exception();
    }
  }
}
