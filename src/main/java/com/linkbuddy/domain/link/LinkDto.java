package com.linkbuddy.domain.link;


import com.linkbuddy.global.entity.Link;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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
  public static class Create {
    private Long id;

    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    @Length(max = 255, message = "글자 수는 최대 255자 이하여야 합니다.")
    @NotNull
    private String name;
    private String description;
    @NotBlank(message = "링크URL은 공백이 아니어야 합니다.")
    @NotNull
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

    @Builder
    public Update(Link l) {
      this.id = l.getId();
      this.name = l.getName();
      this.description = l.getDescription();
      this.linkUrl = l.getLinkUrl();
      this.categoryId = l.getCategoryId();
      this.deleteTf = l.getDeleteTf();
    }
  }
}
