package com.linkbuddy.domain.favorite;

import com.linkbuddy.global.entity.Favorite;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.sql.Timestamp;

/**
 * packageName    : com.linkbuddy.domain.favorite
 * fileName       : FavoriteDto
 * author         : yl951
 * date           : 2024-10-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-12        yl951       최초 생성
 */
public class FavoriteDto {
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CreateFavorite {
    private Long id;
    private Long userId;
    private Long linkId;
    private Timestamp createdAt;

    @Builder
    @QueryProjection
    public CreateFavorite(Favorite f) {
      this.id = f.getId();
      this.userId = f.getUserId();
      this.linkId = f.getLinkId();
      this.createdAt = f.getCreatedAt();
    }
  }


}
