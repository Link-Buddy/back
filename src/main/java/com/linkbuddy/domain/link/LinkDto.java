package com.linkbuddy.domain.link;


import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.entity.Link;
import com.linkbuddy.global.entity.User;
import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.NumberExpression;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

/**
 * packageName    : com.linkbuddy.domain.link
 * fileName       : LinkDto
 * author         : summer
 * date           : 2024-03-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-24        summer          최초 생성
 */

public class LinkDto {
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Mylink {

    private Long linkCount;
    //  private Long favoriteCount;

    @Builder
    @QueryProjection
    public Mylink(Long linkCount) {

      this.linkCount = linkCount;
      //   this.favoriteCount = favoriteCount;
    }

  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Create {
    private Long id;

    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    @Length(max = 255, message = "글자 수는 최대 255자 이하여야 합니다.")
    private String name;
    private String description;
    @NotBlank(message = "링크URL은 공백이 아니어야 합니다.")
    private String linkUrl;
    private Long categoryId;

    @Builder
    public Create(Link l) {
      this.id = l.getId();
      this.name = l.getName();
      this.description = l.getDescription();
      this.linkUrl = l.getLinkUrl();
      this.categoryId = l.getCategoryId();
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Update {
    private Long id;
    private String linkUrl;

    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    @Length(max = 255, message = "글자 수는 최대 255자 이하여야 합니다.")
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Long categoryId;
    @NotNull
    private Boolean deleteTf;
    @NotNull
    private Long userId;

    @Builder
    public Update(Link l) {
      this.id = l.getId();
      this.name = l.getName();
      this.description = l.getDescription();
      this.linkUrl = l.getLinkUrl();
      this.categoryId = l.getCategoryId();
      this.deleteTf = l.getDeleteTf();
      this.userId = l.getUserId();
    }
  }
}
