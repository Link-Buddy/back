package com.linkbuddy.domain.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * packageName    : com.linkbuddy.domain.Category
 * fileName       : CategoryDto
 * author         : yl951
 * date           : 2024-04-13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-13        yl951       최초 생성
 */
public class CategoryDto {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CreatePrivate {
    @NotBlank(message = "폴더명 공백이 아니어야 합니다.")
    @Length(max = 50, message = "글자 수는 최대 50자 이하여야 합니다.")
    @NotNull
    private String groupName;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class UpdatePrivate {
    @NotBlank(message = "폴더명 공백이 아니어야 합니다.")
    @Length(max = 50, message = "글자 수는 최대 50자 이하여야 합니다.")
    @NotNull
    private String groupName;
    private Long fileId;
  }

  public static class DeletePrivate {
    @NotNull
    private Boolean deleteTf;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CreatePublic {

    @NotBlank(message = "폴더명 공백이 아니어야 합니다.")
    @Length(max = 50, message = "글자 수는 최대 50자 이하여야 합니다.")
    @NotNull
    private String groupName;

    @NotBlank(message = "buddyId는 공백이 아니어야 합니다.")
    @NotNull
    private String buddyId;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class UpdatePublic {
    @NotBlank(message = "폴더명 공백이 아니어야 합니다.")
    @Length(max = 50, message = "글자 수는 최대 50자 이하여야 합니다.")
    @NotNull
    private String groupName;
    private Long fileId;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class DeletePublic {

    @NotNull
    private Boolean deleteTf;
  }
}
