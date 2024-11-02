package com.linkbuddy.domain.favorite.repository;

import com.linkbuddy.global.entity.QFavorite;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.linkbuddy.domain.favorite.repository
 * fileName       : FavoriteCustomRepositoryImpl
 * author         : yl951
 * date           : 2024-10-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-12        yl951       최초 생성
 */

@Repository
@RequiredArgsConstructor
public class FavoriteCustomRepositoryImpl implements FavoriteCustomRepository {
  private final JPAQueryFactory query;
  QFavorite favorite = QFavorite.favorite;

  @Override
  public Long findMyFavoriteCount(Long userId) {
    Long result = query.select(favorite.count())
            .from(favorite)
            .where(favorite.userId.eq(userId))
            .groupBy(favorite.userId)
            .fetchOne();

    //데이터 없으면 0개 반환
    return Optional.ofNullable(result).orElse(0L);
  }

  @Override
  public Boolean isExistFavoriteLink(Long linkId, Long userId) {
    Integer fetchOne = query.selectOne()
            .from(favorite)
            .where(favorite.userId.eq(userId).and(favorite.linkId.eq(linkId)))
            .fetchFirst();
    return fetchOne != null;
  }

  @Override
  @Transactional
  public void deleteFavoriteLink(Long linkId, Long userId) {
    query.delete(favorite)
            .where(favorite.userId.eq(userId)
                    .and(favorite.linkId.eq(linkId)))
            .execute();
  }
}
