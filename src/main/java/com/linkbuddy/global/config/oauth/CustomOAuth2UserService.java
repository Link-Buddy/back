package com.linkbuddy.global.config.oauth;

import com.linkbuddy.domain.category.CategoryDto;
import com.linkbuddy.domain.category.CategoryService;
import com.linkbuddy.domain.user.repository.UserRepository;
import com.linkbuddy.global.config.PrincipalDetails;
import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.entity.User;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;

/**
 * packageName    : com.linkbuddy.global.config.oauth
 * fileName       : CustomOAuth2UserService
 * author         : yl951
 * date           : 2024-06-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-02        yl951       최초 생성
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  private final UserRepository userRepository;
  @Autowired
  private CategoryService categoryService;

  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

// 1. 유저 정보(attributes) 가져오기
    OAuth2UserService delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    //2. OAuth2 서비스 id, 식별값 가져오기
    String registrationId = userRequest.getClientRegistration().getRegistrationId();//google
    // Oauth2 로그인 진행 시 키가 되는 필드값 (naver, kakao => application.properties -> user_name_attribute)
    String userNameAttributeName
            = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); //sub (해당 유저 고유식별값)


    // 3. 유저 정보 dto 생성
    OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());


    //4. 회원가입 및 로그인
    try {
      getOrSave(attributes, registrationId);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    //사용자에게 권한을 부여 (USER)
    return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("USER")), attributes.getAttributes(), attributes.getNameAttributeKey());
  }

  private User getOrSave(OAuthAttributes attributes, String registrationId) throws Exception {
    log.info("getOrSave attributes = {}", attributes.toEntity());
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

    Optional<User> existingUser = userRepository.findByEmail(attributes.getEmail());

    User user;
    if (existingUser.isPresent()) {
      // 로그인 처리
      user = existingUser.get().update(attributes.getName(), attributes.getImageUrl())
              .updateLastLoggedAt(null, currentTimestamp, registrationId);
      return userRepository.save(user);
    } else {
      // 회원가입 처리
      user = attributes.toEntity();
      userRepository.save(user);

      // CreatePrivate 객체 생성
      Category category = new Category();
      category.setCategoryName("미분류");

      CategoryDto.CreatePrivate privateDto = CategoryDto.CreatePrivate.builder()
              .c(category)
              .build();
      categoryService.createPrivateCategory(privateDto, user.getId());

      return user;
    }

  }
}
