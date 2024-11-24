package com.linkbuddy.global.config.oauth;

import com.linkbuddy.global.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.linkbuddy.global.config.oauth
 * fileName       : OAuthAttributes
 * author         : yl951
 * date           : 2024-06-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-06        yl951       최초 생성
 */

@Getter
@Slf4j
public class OAuthAttributes {
  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String social;
  private String picture;
  private Integer statusCd;

  @Builder
  public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String social, String picture, Integer statusCd) {
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.social = social;
    this.picture = picture;
    this.statusCd = statusCd;
  }

  public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
    return switch (registrationId) {
      case "google" -> ofGoogle(userNameAttributeName, attributes);
      case "naver" -> ofNaver(userNameAttributeName, attributes);
      default -> null;
    };
  }


  private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

    Map<String, Object> userData = new HashMap<>();

    userData.put("sub", attributes.get("email"));

    return OAuthAttributes.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .picture((String) attributes.get("picture"))
            .social("google")
            .statusCd(10)
            .attributes(userData)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }


  private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");
    log.info("response = {}", response);
    log.info("userNameAttributeName = {}", userNameAttributeName);
    log.info("attributes = {}", attributes);

    // 기준이 되는 user_name의 이름을 네이버에서는 response로 해야 한다.

    Map<String, Object> newAttributes = new HashMap<>();
    newAttributes.put("response", response.get("email"));


    return OAuthAttributes.builder()
            .name((String) response.get("name"))
            .email((String) response.get("email"))
            .picture((String) response.get("profile_image"))
            .social("naver")
            .statusCd(10)
            .attributes(newAttributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }

  public User toEntity() {
    return User.builder()
            .name(name)
            .email(email)
            .social(social)
            .statusCd(statusCd)
            .build();
  }
}
