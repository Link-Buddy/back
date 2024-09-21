package com.linkbuddy.domain.category;

import com.linkbuddy.global.entity.Category;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

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
  public static class PrivateCategory {
    private Long id;
    private String categoryName;
    private Long fileId;
    private Long shareTypeCd;
    private Long userId;
    private Timestamp createdAt;

    @Builder
    @QueryProjection
    public PrivateCategory(Category c) {
      this.id = c.getId();
      this.categoryName = c.getCategoryName();
      this.fileId = c.getFileId();
      this.shareTypeCd = c.getShareTypeCd();
      this.userId = c.getUserId();
      this.createdAt = c.getCreatedAt();
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CreatePrivate {
    private Long id;
    @NotBlank(message = "폴더명 공백이 아니어야 합니다.")
    @Length(max = 50, message = "글자 수는 최대 50자 이하여야 합니다.")
    @NotNull
    private String categoryName;

    @Builder
    public CreatePrivate(Category c) {
      this.id = c.getId();
      this.categoryName = c.getCategoryName();
    }


  }


  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class BuddyCategory {
    private Long id;
    private String categoryName;
    private Long fileId;
    private Long shareTypeCd;
    private Long userId;
    private Long buddyId;
    private Timestamp createdAt;

    @Builder
    @QueryProjection
    public BuddyCategory(Category c) {
      this.id = c.getId();
      this.categoryName = c.getCategoryName();
      this.fileId = c.getFileId();
      this.shareTypeCd = c.getShareTypeCd();
      this.userId = c.getUserId();
      this.buddyId = c.getBuddyId();
      this.createdAt = c.getCreatedAt();
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CreateBuddy {

    @NotBlank(message = "폴더명 공백이 아니어야 합니다.")
    @Length(max = 50, message = "글자 수는 최대 50자 이하여야 합니다.")
    @NotNull
    private String categoryName;

    @NotBlank(message = "buddyId는 공백이 아니어야 합니다.")
    @NotNull
    private Long buddyId;

    @Builder
    public CreateBuddy(Category c) {
      this.categoryName = c.getCategoryName();
      this.buddyId = c.getBuddyId();
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Update {
    @NotNull
    private Long id;
    @NotBlank(message = "폴더명 공백이 아니어야 합니다.")
    @Length(max = 50, message = "글자 수는 최대 50자 이하여야 합니다.")
    @NotNull
    private String categoryName;
    private Long fileId;

    @Builder
    public Update(Category c) {
      this.id = c.getId();
      this.categoryName = c.getCategoryName();
      this.fileId = c.getFileId();
    }
  }

  public static class Delete {
    @NotNull
    private Long id;
    @NotNull
    private Boolean deleteTf;

    @Builder
    public Delete(Category c) {
      this.id = c.getId();
      this.deleteTf = c.getDeleteTf();
    }
  }
}
