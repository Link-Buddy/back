package com.linkbuddy.global.config.oauth;

import com.linkbuddy.global.entity.User;
import lombok.Builder;
import lombok.Getter;

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
public class OAuthAttributes {
  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  @Builder
  public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.picture = picture;
  }

  public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
    return switch (registrationId) {
      case "google" -> ofGoogle(userNameAttributeName, attributes);
      //  case "naver" -> ofNaver(userNameAttributeName, attributes);
      default -> null;
    };
  }


  private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
    return OAuthAttributes.builder()
            .name((String) attributes.get("name"))
            .email((String) attributes.get("email"))
            .picture((String) attributes.get("picture"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }

  public User toEntity() {
    return User.builder()
            .name(name)
            .email(email)
            .build();
  }
}
