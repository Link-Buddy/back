package com.linkbuddy.domain.link;


import com.linkbuddy.global.entity.Category;
import com.linkbuddy.global.entity.Link;
import com.querydsl.core.annotations.QueryProjection;
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
  public static class Mylink {

    private Long registedCount;
    private Long favoriteCount;

    @Builder
    @QueryProjection
    public Mylink(Long registedCount, Long favoriteCount) {

      this.registedCount = registedCount;
      this.favoriteCount = favoriteCount;
    }

  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class SearchResponse {
    private Long linkId;
    private String categoryName;
    private Long shareTypeCd;
    private String buddyName;
    private String linkName;
    private String linkDescription;
    private String linkUrl;

    @Builder
    @QueryProjection
    public SearchResponse(Link l, Category c, String buddyName) {
      System.out.println(l);
      this.linkId = l.getId();
      this.categoryName = c.getCategoryName();
      this.shareTypeCd = c.getShareTypeCd();
      this.buddyName = buddyName;
      this.linkName = l.getName();
      this.linkDescription = l.getDescription();
      this.linkUrl = l.getLinkUrl();
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


  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class LinkInfo {
    private Long id;
    private String name;
    private Long userId;
    private String description;
    private String linkUrl;
    private Long categoryId;
    private Boolean isFavorite; // 즐겨찾기 여부

    @Builder
    public LinkInfo(Link l, Boolean isFavorite) {
      this.id = l.getId();
      this.name = l.getName();
      this.userId = l.getUserId();
      this.description = l.getDescription();
      this.linkUrl = l.getLinkUrl();
      this.categoryId = l.getCategoryId();
      this.isFavorite = isFavorite; // isFavorite 필드 추가
    }

  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class LinkInfoData {
    private Long id;
    private String name;
    private Long userId;
    private String description;
    private String linkUrl;
    private Long categoryId;
    private Boolean isFavorite; // 즐겨찾기 여부
    private String imageUrl; // url image url
    private String urlTitle;  // url title

    @Builder
    public LinkInfoData(LinkDto.LinkInfo lInfo, String imageUrl, String urlTitle) {
      this.id = lInfo.getId();
      this.name = lInfo.getName();
      this.userId = lInfo.getUserId();
      this.description = lInfo.getDescription();
      this.linkUrl = lInfo.getLinkUrl();
      this.categoryId = lInfo.getCategoryId();
      this.isFavorite = lInfo.getIsFavorite();
      this.imageUrl = imageUrl;
      this.urlTitle = urlTitle;
    }

  }
}
