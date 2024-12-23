package com.linkbuddy.domain.user.dto;

import com.linkbuddy.domain.link.LinkDto;
import com.linkbuddy.global.entity.Link;
import com.linkbuddy.global.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * packageName    : com.linkbuddy.domain.user.dto
 * fileName       : UserDTO
 * author         : admin
 * date           : 2024-04-06
 * description    : 회원 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-06        admin       최초 생성
 */
@Data
public class UserDTO {
  private Long id;
  private String email;
  private String name;
  private String password;
  private String social;
  private Integer statusCd;
  private Long fileId;

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class UserInfo {
    private Long id;
    private String email;
    private String name;
    private String imageUrl;
    private Long linkCount;
    private Long favoriteCount;

    @Builder
    public UserInfo(User user, LinkDto.Mylink my) {
      this.id = user.getId();
      this.email = user.getEmail();
      this.name = user.getName();
      this.imageUrl = user.getImageUrl();
      this.linkCount = my.getRegistedCount();
      this.favoriteCount = my.getFavoriteCount();
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String imageUrl;

    @Builder
    @QueryProjection
    public UserResponse(Long id, String name, String email, String imageUrl) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.imageUrl = imageUrl;
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Update {
    private Long id;
    private String name;

    @Builder
    public Update(User u) {
      this.id = u.getId();
      this.name = u.getName();
    }
  }

}
