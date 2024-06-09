package com.linkbuddy.global.config.oauth;

import com.linkbuddy.domain.user.repository.UserRepository;
import com.linkbuddy.global.config.PrincipalDetails;
import com.linkbuddy.global.entity.User;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

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
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  private final UserRepository userRepository;

  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

// 1. 유저 정보(attributes) 가져오기
    OAuth2UserService delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    //OAuth2 서비스 id, name 가져오기
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

    Map<String, Object> originAttributes = oAuth2User.getAttributes();

    // 4. 유저 정보 dto 생성
    OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());


    //5. 회원가입 및 로그인
    User user = getOrSave(attributes);

    return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getEmail())), attributes.getAttributes(), attributes.getNameAttributeKey());

  }

  private User getOrSave(OAuthAttributes attributes) {
    User user = (User) userRepository.findByEmail(attributes.getEmail())
            .map(entity -> entity.update(attributes.getName()))
            .orElse(attributes.toEntity());
    return userRepository.save(user);

  }
}
