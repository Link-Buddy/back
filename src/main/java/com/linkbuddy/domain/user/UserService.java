package com.linkbuddy.domain.user;

import com.linkbuddy.domain.category.CategoryDto;
import com.linkbuddy.domain.category.CategoryService;
import com.linkbuddy.domain.user.dto.UserDTO;
import com.linkbuddy.domain.user.repository.UserRepository;
import com.linkbuddy.global.config.jwt.JwtToken;
import com.linkbuddy.global.config.jwt.JwtTokenProvider;
import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.entity.User;
import com.linkbuddy.global.util.CustomException;
import com.linkbuddy.global.util.StatusEnum;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;

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
  @Autowired
  private CategoryService categoryService;

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

      // CreatePrivate 객체 생성
      Category category = new Category();
      category.setCategoryName("미분류");

      CategoryDto.CreatePrivate privateDto = CategoryDto.CreatePrivate.builder()
              .c(category)
              .build();
      categoryService.createPrivateCategory(privateDto, newUser.getId());

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
      throw new Exception(e);
    }
  }

  public User findById(Long userId) throws Exception {
    try {
      return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user not found." + userId));

    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  @Transactional
  public User updateUser(Long userId, UserDTO.Update updateDto) throws Exception {
    try {
      return userRepository.update(userId, updateDto.getName());

    } catch (Exception e) {
      throw new Exception(e);
    }
  }


  @Transactional
  public boolean changePassword(Long userId, String currentPassword, String newPassword) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    // 현재 비밀번호 확인
    if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
      throw new CustomException(StatusEnum.BAD_REQUEST, "현재 비밀번호가 올바르지 않습니다.");
    }
    if (currentPassword.equals(newPassword)) {
      throw new CustomException(StatusEnum.BAD_REQUEST, "현재 비밀번호와 다른 비밀번호를 입력해주세요");

    }

    // 새 비밀번호 암호화
    String encodedNewPassword = passwordEncoder.encode(newPassword);

    // 비밀번호 업데이트
    user.setPassword(encodedNewPassword);
    userRepository.save(user);

    return true;
  }

  @Transactional
  public JwtToken signIn(String email, String password) {
    try {
      // 1. Authentication 객체 생성
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
      // 2. authenticate 메서드를 통해 요청된 회원정보에 대한 검증 -> loadUserByUsername 메서드 실행됨
      Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
      // 3. 검증에 성공하면 인증된 Authentication 객체 기반으로 JWT 토큰 생성
      JwtToken jwtToken = jwtTokenProvider.createToken(authentication);

      if (jwtToken == null || jwtToken.getAccessToken().isEmpty()) {
        throw new IllegalArgumentException("JWT 토큰 생성에 실패했습니다.");
      }
      //last_logged_at
      Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
      userRepository.findByEmail(email)
              .map(entity -> entity.updateLastLoggedAt(jwtToken.getRefreshToken(), currentTimestamp, null));
      return jwtToken;
    } catch (AuthenticationException e) {
      // 인증 실패 시 예외 발생
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password", e);
    }
  }

}
