package com.linkbuddy.domain.link;


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

    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    @Length(max = 255, message = "글자 수는 최대 255자 이하여야 합니다.")
    @NotNull
    private String name;
    private String description;
    @NotBlank(message = "링크URL은 공백이 아니어야 합니다.")
    @NotNull
    private String linkUrl;
    private Long linkGroupId;

//    @Builder
//    public Create(String name, String description, String linkUrl, Long linkGroupId) {
//      this.name = name;
//      this.description = description;
//      this.linkUrl = linkUrl;
//      this.linkGroupId = linkGroupId;
//    }

  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Update {
    private String name;
    private String description;
    private Long linkGroupId; // 새로운 링크 그룹 ID
    private Boolean deleteTf;

//    public Update(String name, String description, Long linkGroupId, Boolean deleteTf) {
//      this.name = name;
//      this.description = description;
//      this.linkGroupId = linkGroupId;
//      this.deleteTf = deleteTf;
//    }
  }
}
